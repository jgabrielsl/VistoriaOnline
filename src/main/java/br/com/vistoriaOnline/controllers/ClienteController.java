package br.com.vistoriaOnline.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.vistoriaOnline.model.Cliente;
import br.com.vistoriaOnline.model.Imagem;
import br.com.vistoriaOnline.model.ImagemStatus;
import br.com.vistoriaOnline.model.Tipo;
import br.com.vistoriaOnline.model.dto.ImagemTipoDto;
import br.com.vistoriaOnline.model.dto.RestResponse;
import br.com.vistoriaOnline.model.dto.UploadDto;
import br.com.vistoriaOnline.repository.ClienteRepository;
import br.com.vistoriaOnline.repository.ImagemRepository;
import br.com.vistoriaOnline.repository.TipoRepository;

@Controller
@RequestMapping(value = { "/cliente", "/cliente/" })
public class ClienteController {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private TipoRepository tipoRepo;
	
	@Autowired
	private ImagemRepository imgRepo;
	
	@Autowired
	private Environment env;
	
    private final static Pattern mobile_b = Pattern.compile("android.+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern mobile_v = Pattern.compile("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    private final static Pattern mobile_tablet_b = Pattern.compile("android|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(ad|hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino|playbook|silk", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern mobile_tablet_v = Pattern.compile("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);


	@GetMapping(value = { "/{id}", "/{id}/" })
	public String index(HttpServletRequest request, Model model, @PathVariable Long id, @RequestParam(required = true) String tkn) {
		
		if(!isMobileOrTablet(request.getHeader("User-Agent")))
			return "UploadErrorDevice";
		Optional<Cliente> clienteOpt = repo.findById(id);
		
		if(clienteOpt.isPresent() && validTkn(clienteOpt.get(), tkn)) {
			boolean aprovados = true;
			List <ImagemTipoDto> dto = new ArrayList<ImagemTipoDto>();
			List<Tipo> tipos = tipoRepo.listaTiposEnviosCliente(id);
			List<Imagem> imagens = imgRepo.findAllByClienteAndNextIdIsNull(clienteOpt.get());
			boolean completed = true;
			for (Tipo tipo : tipos) {
				//Imagem img = imgRepo.findByTipoAndClienteAndNextIdIsNull(tipo, clienteOpt.get());
				Optional<Imagem> img = imagens.stream().filter(a -> a.getTipo().getId() == tipo.getId()).findFirst();
				Imagem i;
				if(img.isEmpty() || img.get().getStatus() == null || img.get().getStatus().getId().equals(ImagemStatus.AGUARDANDO_ENVIO.getId()) ||
						img.get().getStatus().getId().equals(ImagemStatus.REPROVADO.getId())) {
					completed = false;
					if(img.isEmpty() || img.get().getStatus() == null)
						i = new Imagem(1);
					else
						i = img.get();
					dto.add(new ImagemTipoDto(tipo.getTipo(), tipo.getImagemPadrao(), tipo.getDescricao(), i, tipo.getId(), i.getStatus().getId()));
				}else
					i = img.get();
				
				if(i.getStatus().getId() != ImagemStatus.APROVADO.getId())
					aprovados = false;
			}
			
			model.addAttribute("listaImagens", dto);
			
			if(completed) {
				model.addAttribute("aprovados", aprovados);
				return "UploadCompleto";
			}
			else
				return "Upload";
		}
		
		return "UploadError";
	}
	
	@PostMapping(value = { "/{id}/upload", "/{id}/upload/" }, consumes = "application/json", 
			produces = "application/json")
	public ResponseEntity<RestResponse> uploads(@PathVariable Long id, @RequestBody(required = true) UploadDto upload) {
		Optional<Cliente> clienteOpt = repo.findById(id);
		
		if(clienteOpt.isPresent() && validTkn(clienteOpt.get(), upload.getToken())) {
			
			try {
				byte[] imagem = Base64.getDecoder().decode(upload.getImage64());
				Tipo tipo = new Tipo(upload.getIdTipo());
				Imagem img = new Imagem();
				//img.setImagem(upload.getImage64());
				img.setFileName(env.getProperty("imgsLocation")+clienteOpt.get().getId()+tipo.getId()+"."+upload.getExtensao());
				
				File file = new File(img.getFileName());
				file.createNewFile();
				FileOutputStream fout = new FileOutputStream(file);
				fout.write(imagem);
				fout.close();
				
				img.setTipo(tipo);
				img.setCliente(clienteOpt.get());
				img.setStatus(ImagemStatus.ENVIADO);
				Imagem lastImg = imgRepo.findByTipoAndClienteAndNextIdIsNull(tipo, clienteOpt.get());
				img = imgRepo.save(img);
				if(lastImg != null) {
					lastImg.setNextId(img.getId());
					imgRepo.save(lastImg);
				}
				return new ResponseEntity<RestResponse>(new RestResponse("SUCESSO", "Imagem adicionada com sucesso", null), HttpStatus.CREATED);
			}catch (Exception e) {
				System.out.println("ERRO UPLOAD IMAGEM->");
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<RestResponse>(new RestResponse("Falha", "Falha ao adicionar imagem", null), HttpStatus.CONFLICT);
	}
	
	public boolean validTkn(Cliente cliente, String tkn) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec pvt = new PKCS8EncodedKeySpec(cliente.getPrivateKey());
			PrivateKey priv = keyFactory.generatePrivate(pvt);
			
			//Decryprt
			Cipher decryptCipher = Cipher.getInstance("RSA");
			decryptCipher.init(Cipher.DECRYPT_MODE, priv);
			
			tkn = tkn.replaceAll("-", "\\+").replaceAll("_", "/").replaceAll("\\.", "=");
			
			byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(tkn));
			String token = new String(decryptedMessageBytes, StandardCharsets.UTF_8).replaceAll("#\\*VisTorIa\\*OnlINe\\*#", "");
			
			if(!token.equalsIgnoreCase(cliente.getId().toString())) {
				return false;
			}		
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean isMobileOrTablet(String ua) {
        return true/*ua != null && (mobile_tablet_b.matcher(ua).find() ||
                ua.length() >= 4 && mobile_tablet_v.matcher(ua.substring(0, 4)).find())*/;
    }
	
}
