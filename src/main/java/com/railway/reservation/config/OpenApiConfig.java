package com.railway.reservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI railwayReservationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Railway Reservation System REST API")
                        .description("Comprehensive REST API for managing Passengers, Trains, Stations, Coaches, and Bookings with full CRUD operations, database connectivity, and entity relationships.\n\n"
                                + "### Features:\n"
                                + "- **Stations:** Manage station codes, names, and locations.\n"
                                + "- **Coaches:** Configure coach types (AC 1st, AC 2-Tier, AC 3-Tier, Sleeper) and capacities attached to trains.\n"
                                + "- **Trains:** Configure train routes, schedules, origin/destination stations, and base fares.\n"
                                + "- **Passengers:** Register passengers with contact information and view booking history.\n"
                                + "- **Bookings:** Book train tickets, auto-calculate fares based on coach multiplier and distance, allocate seats, generate PNRs, and manage cancellations.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Railway Reservation System Support")
                                .email("support@railway-reservation.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server")
                ));
    }
}
