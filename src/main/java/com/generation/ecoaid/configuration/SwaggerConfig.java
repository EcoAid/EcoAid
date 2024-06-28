package com.generation.ecoaid.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
//Indica que esta classe contém definições de beans que serão gerenciados pelo Spring
public class SwaggerConfig {
	
	@Bean
	/**
     * Define um bean que configura a documentação da API usando o Swagger.
     * 
     * @return uma instância configurada de OpenAPI com informações da API.
     */
	OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Projeto Integrador - Ecoaid")
						.description("Projeto Integrador Desenvolvido na Generation Brasil")
						.version("v0.0.1")
			    .license(new License()
								.name("Grupo Ecoaid")
								.url("-"))
			    .contact(new Contact()
								.name("Grupo Ecoaid")
								.url("https://github.com/EcoAid")
								.email("grupoecoaid@gmail.com")))
				.externalDocs(new ExternalDocumentation()
						.description("Github")
						        .url("https://github.com/EcoAid/EcoAid"));
	}
	
	@Bean
	   /**
     * Define um bean que personaliza a documentação da API, adicionando respostas padrão
     * para diferentes códigos de status HTTP.
     * 
     * @return uma instância de OpenApiCustomizer.
     */
    OpenApiCustomizer customerGlocalHeaderOpenApiCustomiser () {
        return openApi -> {
            // Itera sobre todos os caminhos (endpoints) e suas operações (métodos HTTP) na API
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                    .forEach(operation -> {

                        ApiResponses apiResponses = operation.getResponses();
                        // Adiciona respostas padrão para diferentes códigos de status HTTP
                        apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
                        apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido"));
                        apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído"));
                        apiResponses.addApiResponse("400", createApiResponse("Erro na requisição!"));
                        apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!"));
                        apiResponses.addApiResponse("403", createApiResponse("Acesso proibido!"));
                        apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado!"));
                        apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));


                    }));
        };
}
	 /**
     * Método auxiliar que cria uma instância de ApiResponse com uma descrição fornecida.
     * 
     * @return uma instância de ApiResponse com a descrição definida.
     */
	  private ApiResponse createApiResponse(String message) {
	        return new ApiResponse().description(message);
	    }
	  
}
