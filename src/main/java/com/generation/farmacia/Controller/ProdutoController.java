package com.generation.farmacia.Controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.Repository.CategoriaRepository;
import com.generation.farmacia.Repository.ProdutoRepository;
import com.generation.farmacia.model.Produto;

@RestController
@RequestMapping ("/produtos")
@CrossOrigin(origins="*", allowedHeaders="*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoringCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
		if (categoriaRepository.existsById(produto.getCategoria().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto){
		if (categoriaRepository.existsById(produto.getCategoria().getId())) {
			return produtoRepository.findById(produto.getId())
					.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
		.orElse(ResponseEntity.notFound().build());
		} else {
			return ResponseEntity.badRequest().build();
		}	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePostagem(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> {
			produtoRepository.deleteById(id); return ResponseEntity.noContent().build();
		}) .orElse(ResponseEntity.notFound().build());
	}
	
	//DESAFIO 1
	//consulta preço inicial e preço final, e aparecer o que estiver entre (BEETWEN) findByStartDateBetween
	
	@GetMapping("/preco/{preco1}-{preco2}")
	public ResponseEntity<List<Produto>> getByPreco(@PathVariable BigDecimal preco1, @PathVariable BigDecimal preco2){
		return ResponseEntity.ok(produtoRepository.findByPrecoBetween(preco1, preco2));
	}
	
	//DESAFIO 2
	//consulta que busque por nome e laboratorio
	
	@GetMapping("/nomelab/{nome}-{laboratorio}")
	public ResponseEntity<List<Produto>> findByNomeLaboratorioProduto (@PathVariable String nome, @PathVariable String laboratorio){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoringCaseAndLaboratorioContainingIgnoringCase(nome,laboratorio));
	}
}
