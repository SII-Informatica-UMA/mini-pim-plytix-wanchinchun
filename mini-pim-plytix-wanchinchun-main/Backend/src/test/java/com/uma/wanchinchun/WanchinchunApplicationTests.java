package com.uma.wanchinchun;

import com.uma.wanchinchun.dtos.RelationDTO;
import com.uma.wanchinchun.models.Relationship;
import com.uma.wanchinchun.repositories.RelationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de relaciones")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WanchinchunApplicationTests {
	@Autowired
    private TestRestTemplate restTemplate;
	private Relationship relacionPrueba;
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private RelationRepository relationRepo;
	@Test
	void contextLoads() throws InterruptedException {
		System.out.println("Test for loading pim-schema.sql started...");
		Thread.sleep(5000);
		System.out.println("Test for loading pim-schema.sql finished.");
	}

	@BeforeEach
    public void initializeDatabase() {
        relationRepo.deleteAll();
    }

	private URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }

	private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }


    @Nested
    @DisplayName("Cuando esta vacia la base de datos")
    public class RelacionesVacio {
        @Test
        @DisplayName("Devuelve una relacion vacia")
        public void devuelveRelacion() {
            var peticion = get("http", "localhost", port, "/relacion");
            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<List<Relationship>>() {
                    });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().isEmpty());
        }
        @Test
        @DisplayName("Crea relacion")
        public void creaRelacion() {
            var relacion = RelationDTO.builder().nombre("Prueba").build();
            var peticion = post("http", "localhost", port, "/relacion", relacion);
            var respuesta = restTemplate.exchange(peticion,
                    Void.class);
            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
        }
		@Test
        @DisplayName("Modificar una relacion no existente")
        public void modificaRelacion() {
            var relacion = RelationDTO.builder().nombre("Prueba").build();
            var peticion = put("http", "localhost", port, "/relacion/" + 1000, relacion);
            var respuesta = restTemplate.exchange(peticion,
                    Void.class);
            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }
		@Test
        @DisplayName("Borra una relacion inexistente")
        public void borraRelacion() {
            var evento = RelationDTO.builder().nombre("Prueba").build();
            var peticion = delete("http", "localhost", port, "/relacion/" + 1000);
            var respuesta = restTemplate.exchange(peticion,
                    Void.class);
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        /*@Test
        @DisplayName("crea una relacion ya existente")
        public void creaEventoExistente() {
            var evento = EventoDTO.builder().nombre("santaRita").build();
            var peticion = post("http", "localhost", port, "/eventos", evento);
            var respuesta = restTemplate.exchange(peticion,
                    Void.class);

            //assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
            respuesta = restTemplate.exchange(peticion,
                    Void.class);
            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }*/
    }

    @Nested
    @DisplayName("Cuando hay relaciones")
    public class RelacionesLleno {
        @BeforeEach
        public void insertarRelacion() {
            relacionPrueba = new Relationship();
            relacionPrueba.setNombre("Prueba");
			relacionPrueba.setDescription("descripcionPrueba");
            relationRepo.save(relacionPrueba);

			var relacionPrueba2 = new Relationship();
            relacionPrueba2.setNombre("Prueba2");
			relationRepo.save(relacionPrueba2);
        }
        @Test
        @DisplayName("Devuelve una relacion")
        public void devuelveRelacion() {
            var peticion = get("http", "localhost", port, "/relacion/" + relacionPrueba.getId());
            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RelationDTO>() {
                    });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody().getNombre()).isEqualTo("Prueba");
			assertThat(respuesta.getBody().getDescripcion()).isEqualTo("descripcionPrueba");
        }
        @Test
        @DisplayName("Devuelve una relacion que no existe")
        public void devuelveRelacionNoExistente() {
            var peticion = get("http", "localhost", port, "/relacion/" + relacionPrueba.getId() + 1);
            var respuesta = restTemplate.exchange(peticion,
                    new ParameterizedTypeReference<RelationDTO>() {
                    });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
		@DisplayName("Modifica una relacion")
		public void modificarRelacionExistente() {
			var relacion = RelationDTO.builder().nombre("Prueba2").build();
			var peticion = put("http", "localhost",port, "/relacion/"+ relacionPrueba.getId(), relacion);
			var respuesta = restTemplate.exchange(peticion, Void.class);
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
		}

        @Test
		@DisplayName("Modifica una relacion que no existe")
		public void modificarRelacionExistenteError() {
			var relacion = RelationDTO.builder().nombre("Rosse").build();
			var peticion = put("http", "localhost",port, "/relacion/"+ relacionPrueba.getId() + 1, relacion);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

        @Test
		@DisplayName("Modifica una relacion cuyo nombre ya existe")
		public void modificarRelacionExistenteErrorNombre() {
			var relacion = RelationDTO.builder().nombre("relacionPrueba2").build();
			var peticion = put("http", "localhost",port, "/relacion/"+ relacionPrueba.getId(), relacion);

			var respuesta = restTemplate.exchange(peticion, Void.class);

			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
		}

        @Test
		@DisplayName("Devuelve error al eliminar una relacion que no existe")
		public void eliminarRelacionError() {
			var peticion = delete("http", "localhost",port, "/relacion/" + 1000);
			var respuesta = restTemplate.exchange(peticion, Void.class);
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

		@Test
		@DisplayName("Elimina una relacion")
		public void eliminarRelacion() {
			var peticion = delete("http", "localhost",port, "/relacion/" + relacionPrueba.getId());
			var respuesta = restTemplate.exchange(peticion, Void.class);
            assertThat(respuesta.getStatusCode().value()).isEqualTo(204);
		}
    }
}
