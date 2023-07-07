package com.rajeshkawali.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author Rajesh_Kawali
 * 
 */
@OpenAPIDefinition(info = @Info(contact = @Contact(
		name = "Currencies Direct", 
		email = "mumbai@currenciesdirect.com", 
		url = "https://currenciesdirect.com"), 
		description = "OpenApi documentation for Customer service", 
		title = "Currencies Direct - Customer API", 
		version = "1.0", 
		license = @License(
				name = "Currencies Direct Licence", 
				url = "https://www.currenciesdirect.com/en/info/privacy-policy"), 
				termsOfService = "https://www.currenciesdirect.com/en/info/terms-of-use"), 
		servers = {
		@Server(description = "LOCAL Environment", url = "http://localhost:8181"),
		@Server(description = "PROD Environment", url = "https://currenciesdirect.com/") }, 
		security = {
				@SecurityRequirement(name = "bearerAuth") })
@SecurityScheme(name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfiguration {
}
