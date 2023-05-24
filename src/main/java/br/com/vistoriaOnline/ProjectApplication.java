package br.com.vistoriaOnline;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.vistoriaOnline.model.ImagemStatus;
import br.com.vistoriaOnline.model.Role;
import br.com.vistoriaOnline.model.Tipo;
import br.com.vistoriaOnline.model.Usuario;
import br.com.vistoriaOnline.repository.ImagemStatusRepository;
import br.com.vistoriaOnline.repository.RoleRepository;
import br.com.vistoriaOnline.repository.TipoRepository;
import br.com.vistoriaOnline.repository.UsuarioRepository;

@SpringBootApplication
public class ProjectApplication {

	@Autowired
	private UsuarioRepository repo;

	@Autowired
	private RoleRepository repoR;

	@Autowired
	private TipoRepository repo1;
	
	@Autowired
	private ImagemStatusRepository repo2;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean() {
		return (args) -> {

			Role role = repoR.getRoleByName("ADMIN");
			if (role == null) {
				role = new Role();
				role.setName("ADMIN");
				role = repoR.save(role);
			}

			Role role1 = repoR.getRoleByName("USER");
			if (role1 == null) {
				role1 = new Role();
				role1.setName("USER");
				role1 = repoR.save(role1);
			}

			Usuario aux = repo.getUserByEmail("admin@admin.com");
			if (aux == null) {
				Usuario user = new Usuario();
				user.setEmail("admin@admin.com");
				user.setSenha(passwordEncoder().encode("1234"));
				Set<Role> set = new HashSet<>();
				set.add(role);
				user.setRoles(set);
				user.setAtivo(true);
				System.out.println("Admin - inicinado save");
				repo.save(user);
				System.out.println("Admin - save completo");
			} else if (!aux.isAtivo()) {
				aux.setAtivo(true);
				repo.save(aux);
			}
			aux = repo.getUserByEmail("user@user.com");
			if (aux == null) {
				Usuario user1 = new Usuario();
				user1.setEmail("user@user.com");
				user1.setSenha(passwordEncoder().encode("1234"));
				Set<Role> set = new HashSet<>();
				set.add(role1);
				user1.setRoles(set);
				user1.setAtivo(true);
				System.out.println("User - inicinado save");
				repo.save(user1);
				System.out.println("User - save completo");
			} else if (!aux.isAtivo()) {
				aux.setAtivo(true);
				repo.save(aux);
			}
			if(repo1.count() <= 0) {
				URL res = getClass().getClassLoader().getResource("static/vo/img/carros");
				try (Stream<Path> paths = Files.walk(Paths.get(res.toURI()))) {
					paths.filter(Files::isRegularFile).forEach(a -> {
						Tipo tipo = new Tipo();
						try {
							String byt = Base64.getEncoder().encodeToString(Files.readAllBytes(a));
							String name = a.toFile().getName();
							if (name.indexOf("png") >= 0 && name.indexOf("1.") < 0 && name.indexOf("2.") < 0
									&& name.indexOf("3.") < 0) {
								System.out.println(byt.length());
								tipo.setImagemPadrao(byt);
								tipo.setDescricao(name.substring(0, name.indexOf(".png")));
								repo1.save(tipo);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			repo2.save(ImagemStatus.AGUARDANDO_ENVIO);
			repo2.save(ImagemStatus.APROVADO);
			repo2.save(ImagemStatus.ENVIADO);
			repo2.save(ImagemStatus.REPROVADO);
		};

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailServiceImp();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

}
