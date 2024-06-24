package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.*;
import com.grupo6.keepInventory.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acoes")
public class AcaoController {
    private final AcaoRepository acaoRepository;
    private final TipoacaoRepository tipoAcaoRepository;
    private final LocalizacaoRepository localizacaoRepository;
    private final ItemRepository itemRepository;
    private final AnexoRepository anexoRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    public AcaoController(AcaoRepository acaoRepository, TipoacaoRepository tipoAcaoRepository, LocalizacaoRepository localizacaoRepository, ItemRepository itemRepository, AnexoRepository anexoRepository, DisponibilidadeRepository disponibilidadeRepository) {
        this.acaoRepository = acaoRepository;
        this.tipoAcaoRepository = tipoAcaoRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.itemRepository = itemRepository;
        this.anexoRepository = anexoRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }


    @GetMapping
    public List<Acao> acoes() {
        return acaoRepository.findAll();
    }

    @GetMapping("/{itemid}")
    public ResponseEntity<Page<Acao>> getByItem(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @PathVariable Long itemid){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Acao> listaAcoes = acaoRepository.findByItem(itemid, pageable);
        return ResponseEntity.ok(listaAcoes);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping
    public Acao adicionar(@RequestBody Acao acao) {
        Tipoacao tipoAcao = tipoAcaoRepository.findById(acao.getTipoacao().getId())
                .orElseThrow(() -> new RuntimeException("Tipoacao não encontrado"));
        acao.setTipoacao(tipoAcao);




        Localizacao localizacao = localizacaoRepository.findById(acao.getLocalizacao().getId())
                .orElseThrow(() -> new RuntimeException("Localizacao não encontrada"));
        acao.setLocalizacao(localizacao);

        Item item = itemRepository.findById(acao.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        acao.setItem(item);
        if(tipoAcao.getId()==1){
            item.setDisponibilidade(disponibilidadeRepository.findById(Long.valueOf(2)).get());
        } else if(tipoAcao.getId()==2){
            item.setDisponibilidade(disponibilidadeRepository.findById(Long.valueOf(1)).get());
        }
        itemRepository.save(item);

        //        Anexo anexo = anexoRepository.findById(acao.getAnexo().getId())
        //                  .orElseThrow(() -> new RuntimeException("Anexo não encontrado"));
        //        acao.setAnexo(anexo);

        return acaoRepository.save(acao);
    }

    @PutMapping("/{id}")
    public Acao atualizarAcao(@PathVariable Long id, @RequestBody Acao acaoAtualizado) {
        return acaoRepository.findById(id)
                .map(acao -> {
                    acao.setDescricao(acaoAtualizado.getDescricao());
                    acao.setId(acaoAtualizado.getId());
                    acao.setDataEmprestimo(acaoAtualizado.getDataEmprestimo());
                    acao.setDataDevolucao(acaoAtualizado.getDataDevolucao());
                    acao.setEntidade(acaoAtualizado.getEntidade());


                    Tipoacao tipoAcao = tipoAcaoRepository.findById(acaoAtualizado.getTipoacao().getId())
                            .orElseThrow(() -> new RuntimeException("Tipoacao não encontrado"));
                    acao.setTipoacao(tipoAcao);

                    Localizacao localizacao = localizacaoRepository.findById(acaoAtualizado.getLocalizacao().getId())
                            .orElseThrow(() -> new RuntimeException("Localizacao não encontrada"));
                    acao.setLocalizacao(localizacao);

                    Item item = itemRepository.findById(acaoAtualizado.getItem().getId())
                            .orElseThrow(() -> new RuntimeException("Item não encontrado"));
                    acao.setItem(item);

//                    Anexo anexo = anexoRepository.findById(acaoAtualizado.getAnexo().getId())
//                            .orElseThrow(() -> new RuntimeException("Anexo não encontrado"));
//                    acao.setAnexo(anexo);


                    return acaoRepository.save(acao);
                })
                .orElseThrow(() -> new RuntimeException("Acao não encontrada com o id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deletarAcao(@PathVariable Long id) {
        acaoRepository.deleteById(id);
    }
}
