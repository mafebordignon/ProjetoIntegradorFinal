package com.grupo6.keepInventory.ServiceTest;

import com.grupo6.keepInventory.Model.Categoria;
import com.grupo6.keepInventory.RepositoryTest.CategoriaRepository;
import com.grupo6.keepInventory.Service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CamposAtivosService {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrar() {
        Categoria categoria = new Categoria();
        categoria.setNome("Test Categoria");
        categoria.setAtivo(true);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<Categoria> created = categoriaService.cadastrar(categoria);
        assertThat(created.getBody()).isNotNull();
        assertThat(created.getBody().getNome()).isEqualTo("Test Categoria");
    }

    @Test
    public void testCadastrarSemColocarNome() {
        Categoria categoria = new Categoria();

        categoria.setAtivo(true);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<Categoria> created = categoriaService.cadastrar(categoria);
        assertThat(created.getStatusCode()).isNotEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCadastrarSemColocarAtivo(){
        Categoria categoria = new Categoria();
        categoria.setNome("Test Categoria");
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<Categoria> created = categoriaService.cadastrar(categoria);
        assertThat(created.getBody()).isNotNull();
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCadastrarAtivoDiferente(){
        Categoria categoria = new Categoria();
        categoria.setNome("Test Categoria");
        categoria.setAtivo(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<Categoria> created = categoriaService.cadastrar(categoria);
        assertThat(created.getBody()).isNotNull();
        assertThat(created.getBody().isAtivo()).isEqualTo(false);
    }

    @Test
    public void testCadastrarNomeJaExistente(){
        Categoria categoria = new Categoria();
        categoria.setNome("Test Categoria");
        categoria.setAtivo(false);

        when(categoriaRepository.existsByNome("Test Categoria")).thenReturn(true);

        ResponseEntity<Categoria> created = categoriaService.cadastrar(categoria);
        assertThat(created.getStatusCode()).isNotEqualTo(HttpStatus.OK);
        assertThat(created.getBody()).isNull();
    }

    @Test
    public void testAlterarNome() {
        Long categoriaId = 1L;
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(categoriaId);
        categoriaExistente.setNome("Old Name");
        categoriaExistente.setAtivo(true);

        Categoria categoriaAlterada = new Categoria();
        categoriaAlterada.setId(categoriaId);
        categoriaAlterada.setNome("New Name");
        categoriaAlterada.setAtivo(true);

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaAlterada);

        ResponseEntity<Categoria> updated = categoriaService.alterarNome(categoriaId, "New Name");

        assertThat(updated.getBody()).isNotNull();
        assertThat(updated.getBody().getNome()).isEqualTo("New Name");
        assertThat(updated.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testAlterarCategoriaNaoExiste() {
        Long categoriaId = 3L;
        String novoNome = "New Name";

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        ResponseEntity<Categoria> response = categoriaService.alterarNome(categoriaId, novoNome);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testAlterarNomeJaExistente() {
        Long categoriaId = 1L;
        String nomeExistente = "Existing Name";
        String novoNome = "Existing Name";

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(categoriaId);
        categoriaExistente.setNome("Old Name");
        categoriaExistente.setAtivo(true);

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaRepository.existsByNome(novoNome)).thenReturn(true);

        ResponseEntity<Categoria> response = categoriaService.alterarNome(categoriaId, novoNome);

        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testAtivar() {
        Long categoriaId = 1L;

        Categoria categoriaDesativada = new Categoria();
        categoriaDesativada.setId(categoriaId);
        categoriaDesativada.setNome("Test Categoria");
        categoriaDesativada.setAtivo(false);

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaDesativada));

        ResponseEntity<Categoria> response = categoriaService.toggleCategoria(categoriaId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isAtivo()).isTrue();
    }

    @Test
    public void testDesativar() {
        Long categoriaId = 1L;

        Categoria categoriaAtivada = new Categoria();
        categoriaAtivada.setId(categoriaId);
        categoriaAtivada.setNome("Test Categoria");

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoriaAtivada));

        ResponseEntity<Categoria> response = categoriaService.toggleCategoria(categoriaId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isAtivo()).isFalse();
    }

    @Test
    public void testAtivarCategoriaNaoExiste() {
        Long categoriaId = 1L;

        Categoria categoriaAtivada = new Categoria();
        categoriaAtivada.setId(categoriaId);
        categoriaAtivada.setNome("Test Categoria");

        when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        ResponseEntity<Categoria> response = categoriaService.toggleCategoria(categoriaId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testBuscarPorNome() {
        String nomeCategoria = "Categoria Teste";

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(1L);
        categoriaExistente.setNome(nomeCategoria);
        categoriaExistente.setAtivo(true);

        when(categoriaRepository.findByNome(nomeCategoria)).thenReturn(Optional.of(categoriaExistente));

        ResponseEntity<?> categoriaEncontrada = categoriaService.buscarPorNome(nomeCategoria);
        System.out.println(categoriaEncontrada );

        assertThat(categoriaEncontrada.getBody()).isEqualTo(categoriaExistente);
    }

    @Test
    void testBuscarPorNomeCategoriaNaoEncontrada() {
        String nomeCategoria = "Categoria Inexistente";

        when(categoriaRepository.findByNome(nomeCategoria)).thenReturn(Optional.empty());

        ResponseEntity<Categoria> categoriaEncontrada = categoriaService.buscarPorNome(nomeCategoria);

        assertThat(categoriaEncontrada.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    void testPegarItensPaginados() {
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNome("Categoria 1");
        categoria1.setAtivo(true);

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Categoria 2");
        categoria2.setAtivo(true);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Categoria> page = new PageImpl<>(Arrays.asList(categoria1, categoria2), pageable, 2);

        when(categoriaRepository.findAll(pageable)).thenReturn(page);

        Page<Categoria> result = categoriaService.pegarItensPaginados(0, 2);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0)).isEqualTo(categoria1);
        assertThat(result.getContent().get(1)).isEqualTo(categoria2);
    }
}
