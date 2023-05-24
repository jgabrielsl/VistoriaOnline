package br.com.vistoriaOnline.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vistoriaOnline.model.Usuario;
import br.com.vistoriaOnline.model.dto.RestResponse;
import br.com.vistoriaOnline.model.dto.UsuarioDto;
import br.com.vistoriaOnline.repository.UsuarioRepository;
import br.com.vistoriaOnline.services.EmailServiceImp;

@Controller
@RequestMapping(value = { "/cadastro/usuario", "/cadastro/usuario/" })
public class CadastroUsuarioController {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailServiceImp email;

	@GetMapping(value = { "/", "" })
	public String crud(Model model, Principal principal) {
		List<Usuario> usuarios = (List<Usuario>) repo.findAll();
		model.addAttribute("usuarios", usuarios);
		return "cadastrousuario";
	}

	@PostMapping(value = { "/", "" })
	@ResponseBody
	public ResponseEntity<RestResponse> cadastrar(@RequestBody UsuarioDto user) {
		Usuario usuario = user.buildUsuario();
		String senhaGerada = generateSenha();
		usuario.setSenha(passwordEncoder.encode(senhaGerada));
		usuario = repo.save(usuario);
		email.sendSimpleMessage(usuario.getEmail(), "Cadastro na plataforma Vistoria Online", "Olá,\n\nSeguem os dados para acesso "
				+ "a plataforma e converter clientes.\nUtilize seu email e a senha a seguir para efetuar o acesso:\n\n"+senhaGerada+"\n\nSucesso!");
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "USUÁRIO CRIADO COM SUCESSO", senhaGerada),
				HttpStatus.CREATED);
	}

	@GetMapping("/editar/{id}")
	public ResponseEntity<RestResponse> editarFind(@PathVariable Long id) {
		Optional<Usuario> optional = repo.findById(id);
		try {
			if (!optional.isPresent()) 
				return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ENCONTRADO", null),
						HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ENCONTRADO", null),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<RestResponse>(
				new RestResponse("SUCESSO", "USUÁRIO ENCONTRADO COM SUCESSO", new UsuarioDto(optional.get())),
				HttpStatus.OK);
	}

	@PostMapping("/editar")
	public ResponseEntity<RestResponse> editar(@RequestBody UsuarioDto usuario) {
		try {
			Optional<Usuario> optional = repo.findById(usuario.getId());

			if (optional.isPresent()) {
				repo.save(usuario.buildUsuario());
			} else
				return new ResponseEntity<RestResponse>(
						new RestResponse("FALHA", "USUÁRIO PARA EDIÇÂO NÂO ENCONTRADO", null), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(
					new RestResponse("FALHA", "USUÁRIO PARA EDIÇÂO NÂO ENCONTRADO", null), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "USUÁRIO EDITADO COM SUCESSO", null),
				HttpStatus.CREATED);
	}

	@PostMapping("/atualizar/{id}")
	private ResponseEntity<RestResponse> atualizar(@PathVariable Long id) {
		String senhaGerada = generateSenha();
		try {
			Optional<Usuario> jac = repo.findById(id);
			if (jac.isPresent()) {
				Usuario usuario = jac.get();

				usuario.setSenha(passwordEncoder.encode(senhaGerada));
				usuario = repo.save(usuario);
				email.sendSimpleMessage(usuario.getEmail(), "Atualização na plataforma Vistoria Online", "Olá,\n\nSeguem os dados para acesso "
						+ "a plataforma e converter clientes.\nUtilize seu email e a senha a seguir para efetuar o acesso:\n\n"+senhaGerada+"\n\nSucesso!");
			} else
				return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ENCONTRADO", null),
						HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ENCONTRADO", null),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "SENHA DO USUÁRIO ALTERADA", senhaGerada),
				HttpStatus.CREATED);

	}

	@PostMapping("/delete/{id}")
	public ResponseEntity<RestResponse> remover(@PathVariable Long id) {
		try {
			Optional<Usuario> usuario = repo.findById(id);
			if (usuario.isPresent()) {
				repo.deleteById(id);

			} else
				return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ENCONTRADO", null),
						HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ENCONTRADO", null),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "USUÁRIO DELETADO", null),
				HttpStatus.CREATED);
	}
	
	@PostMapping("/ativar/{id}")
	public ResponseEntity<RestResponse> ativar(@PathVariable Long id) {
		try {
			Optional<Usuario> op = repo.findById(id);
			if (op.isPresent()) {
				Usuario usuario = op.get();
				usuario.setAtivo(true);
				repo.save(usuario);
			} else
				return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ATIVADO", null),
						HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO ATIVADO", null),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "USUÁRIO ATIVO", null),
				HttpStatus.CREATED);
	}
	
	@PostMapping("/desativar/{id}")
	public ResponseEntity<RestResponse> desativar(@PathVariable Long id) {
		try {
			Optional<Usuario> op = repo.findById(id);
			if (op.isPresent()) {
				Usuario usuario = op.get();
				usuario.setAtivo(false);
				repo.save(usuario);
			} else
				return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO DESATIVADO", null),
						HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "USUÁRIO NÃO DESATIVADO", null),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "USUÁRIO DESATIVADO", null),
				HttpStatus.CREATED);
	}

	private String generateSenha() {
		Random random = new Random();

		String senhaGerada = random.ints(97, 122 + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(10).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		return senhaGerada;
	}

}
