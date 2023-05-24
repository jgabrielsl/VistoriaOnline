package br.com.vistoriaOnline.controllers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vistoriaOnline.model.Cliente;
import br.com.vistoriaOnline.model.Imagem;
import br.com.vistoriaOnline.model.ImagemStatus;
import br.com.vistoriaOnline.model.Tipo;
import br.com.vistoriaOnline.model.dto.CliDto;
import br.com.vistoriaOnline.model.dto.ClienteDto;
import br.com.vistoriaOnline.model.dto.ImagemTipoDto;
import br.com.vistoriaOnline.model.dto.RestResponse;
import br.com.vistoriaOnline.repository.ClienteRepository;
import br.com.vistoriaOnline.repository.ImagemRepository;
import br.com.vistoriaOnline.repository.TipoRepository;

@Controller
@RequestMapping(value = { "/cadastro/cliente", "/cadastro/cliente/" })
public class CadastroClienteController {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private Environment env;

	@Autowired
	private TipoRepository tipoRepo;
	
	@Autowired
	private ImagemRepository imgRepo;

	@GetMapping(value = { "/", "" })
	public String crud(Model model, Principal principal) {
		List<Cliente> clientes = (List<Cliente>) repo.findAllByOrderByIdDesc();
		model.addAttribute("clientes", clientes);
		return "cadastrocliente";
	}

	@PostMapping(value = { "/", "" })
	@ResponseBody
	public ResponseEntity<RestResponse> cadastrar(@RequestBody ClienteDto clientedto) {
		List<String> vali = clientedto.validaCampos();
		Cliente cliente = clientedto.buildUsuario();
		cliente = repo.save(cliente);
		
		if(!vali.isEmpty()) {
			return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "PREENCHA TODOS OS CAMPOS", vali),
					HttpStatus.FAILED_DEPENDENCY);
		}
		
		generateKeys(cliente);
		repo.save(cliente);
		
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "CLIENTE CRIADO COM SUCESSO", new CliDto(cliente.getId(), cliente.getNome(), cliente.getTelefone(),env.getProperty("basePath")+ cliente.getLink())),
				HttpStatus.CREATED);
		
	}

	@GetMapping("/progresso/{id}")
	public String editarFind(Model model, @PathVariable Long id) {
		Optional<Cliente> optional = repo.findById(id);
		List <ImagemTipoDto> dto = new ArrayList<ImagemTipoDto>();
		List<Tipo> tipos = new ArrayList<Tipo>();
		try {
			if (optional.isPresent()) {	
				tipos = tipoRepo.listaTipos(id);
				List<Imagem> imagens = imgRepo.findAllByClienteAndNextIdIsNull(optional.get());
				Imagem img = new Imagem(1);
				for (Tipo tipo : tipos) {
					Optional<Imagem> imgAux = imagens.stream().filter(a -> a.getTipo().getId() == tipo.getId()).findFirst();
					
					if(imgAux.isPresent())
						img = imgAux.get();
					else
						img = new Imagem(1);
					
					dto.add(new ImagemTipoDto(tipo.getDescricao(), img.getId(), tipo.getId(), 
							img.getStatus().getId(),img.getStatus().getDescricao(), null, 
							null, img.getId()));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("id", id);
		model.addAttribute("imagens", dto);
		model.addAttribute("link", env.getProperty("basePath")+optional.get().getLink());
		model.addAttribute("telefoneCli", optional.get().getTelefone());
		model.addAttribute("nomeCli", optional.get().getNome());
		
		return "modals/modalClienteEnvios";
	}
	
	@GetMapping("/progresso/imagem/{id}")
	public void fromDatabaseAsResEntity(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

		Optional<Imagem> img = imgRepo.findById(id);
		
		if (img.isPresent()) {
			String flName = img.get().getFileName();
			
	        try (InputStream is = new FileInputStream(flName)) {
	
	            // it is the responsibility of the container to close output stream
	            OutputStream os = response.getOutputStream();
	
                response.setContentType(MediaType.parseMediaType("image/"+flName.substring(flName.indexOf("."))).getType());

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
	        } catch (Exception e) {
				System.out.println("ERRO SERVING IMAGE->");
				e.printStackTrace();
			}
        }
	}
	@PostMapping("/editar")
	public ResponseEntity<RestResponse> editar(@RequestBody ClienteDto cliente) {
		List<String> vali = cliente.validaCampos();
		Cliente cli;
		try {
			Optional<Cliente> optional = repo.findById(cliente.getId());

			if (optional.isPresent()) {
				cli = optional.get();
				cliente.editUsuario(cli);
				if(!vali.isEmpty()) {
					return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "PREENCHA TODOS OS CAMPOS", vali),
							HttpStatus.FAILED_DEPENDENCY);
				}
				//generateKeys(cli);
				repo.save(cli);
			} else
				return new ResponseEntity<RestResponse>(
						new RestResponse("FALHA", "CLIENTE PARA EDIÇÂO NÂO ENCONTRADO", null), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(
					new RestResponse("FALHA", "CLIENTE PARA EDIÇÂO NÂO ENCONTRADO", null), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "CLIENTE EDITADO COM SUCESSO", new CliDto(cli.getId(), cli.getNome(), cli.getTelefone(),env.getProperty("basePath")+ cli.getLink())),
				HttpStatus.CREATED);
	}

	@GetMapping("/editar/{id}")
	public ResponseEntity<RestResponse> progreso(@PathVariable Long id) {
		Optional<Cliente> optional = repo.findById(id);
		try {
			if (!optional.isPresent()) 
				return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "FALHA AO VER O PROGRESSO", null),
						HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse>(new RestResponse("FALHA", "FALHA AO VER O PROGRESSO", null),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<RestResponse>(
				new RestResponse("SUCESSO", "ESTE É O PROGRESSO DO CLIENTE", new ClienteDto(optional.get())),
				HttpStatus.OK);
	}
	
	@GetMapping("/download/{id}")
	public void download(HttpServletResponse resp, @PathVariable Long id) {
		Optional<Cliente> optional = repo.findById(id);
		try {
			if (optional.isPresent()) {
				Cliente cli = optional.get();
				
				resp.setContentType("application/zip");
				resp.setHeader("Content-Disposition", "attachment; filename="+cli.getNome()+"_imagens.zip");
				
				ServletOutputStream out = resp.getOutputStream();
				
				ZipOutputStream zipOut = new ZipOutputStream(out);
				
				List<Imagem> imgs = imgRepo.encontraPorClienteAndNextIdIsNull(cli.getId());
				
				for(Imagem img : imgs) {
					String flName = img.getFileName();
					
					try (InputStream is = new FileInputStream(flName)) {			
						ZipEntry zipEntry = new ZipEntry(img.getTipo().getDescricao()+"."+flName.substring(flName.lastIndexOf(".")));
			            zipOut.putNextEntry(zipEntry);
			            zipOut.write(is.readAllBytes());
			            
			            
			        } catch (Exception e) {
						System.out.println("ERRO DOWNLOAD ZIP IMAGE->");
						e.printStackTrace();
					}
					zipOut.closeEntry();
				}
				zipOut.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@PostMapping("/ativar/{id}")
	public ResponseEntity<RestResponse> ativar(@PathVariable Long id) {
		try {
			Optional<Cliente> op = repo.findById(id);
			if (op.isPresent()) {
				Cliente cliente = op.get();
				cliente.setAtivo(true);
				repo.save(cliente);
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
			Optional<Cliente> op = repo.findById(id);
			if (op.isPresent()) {
				Cliente cliente = op.get();
				cliente.setAtivo(false);
				repo.save(cliente);
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
	
	@PostMapping("/imagem/{statusId}/{id}")
	public ResponseEntity<RestResponse> aprovacaoImagem(@PathVariable Integer statusId, @PathVariable Long id) {
		Imagem img;
		try {
			Optional<Imagem> optional = imgRepo.findById(id);

			if (optional.isPresent()) {
				img = optional.get();
				img.setStatus(ImagemStatus.getStatus(statusId));
				imgRepo.save(img);
			} else
				return new ResponseEntity<RestResponse>(
						new RestResponse("FALHA", "IMAGEM NÂO ENCONTRADA", null), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<RestResponse>(
					new RestResponse("FALHA", "ERRO AO PROCESSAR STATUS IMAGEM", null), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "STATUS IMAGEM PROCESSADA COM SUCESSO", "SUCESSO"),
				HttpStatus.CREATED);
	}
	
	private void generateKeys(Cliente cliente) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(512);
			KeyPair pair = generator.generateKeyPair();
	
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();	
	
			Cipher encryptCipher = Cipher.getInstance("RSA");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
	
			String sec = "#*VisTorIa*OnlINe*#";
			byte[] secretMessageBytes = (sec+cliente.getId()+sec).getBytes(StandardCharsets.UTF_8);
			byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
			
			String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes).replaceAll("\\+", "-").replaceAll("/", "_").replaceAll("=", ".");
			cliente.setLink("/cliente/"+cliente.getId()+"?tkn="+encodedMessage);
			cliente.setPrivateKey(privateKey.getEncoded());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
