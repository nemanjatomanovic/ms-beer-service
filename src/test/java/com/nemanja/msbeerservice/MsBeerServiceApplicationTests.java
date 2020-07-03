package com.nemanja.msbeerservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemanja.msbeerservice.bootstrap.BeerLoader;
import com.nemanja.msbeerservice.services.BeerServices;
import com.nemanja.msbeerservice.web.model.BeerDto;
import com.nemanja.msbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class MsBeerServiceApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    BeerServices beerServices;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation).uris()
                        .withScheme("https")
                        .withHost("examplehost")
                        .withPort(4443))
                .build();
    }

    @Test
    public void getBeerTest() throws Exception {
        when(beerServices.getById(any())).thenReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get", pathParameters(
                        parameterWithName("beerId").description("UUID of desired beer to get")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("creationDate").description("Date created"),
                                fieldWithPath("lastModifiedDate").description("Date updated"),
                                fieldWithPath("beerName").description("Name of the beer"),
                                fieldWithPath("beerStyle").description("Type of the beer"),
                                fieldWithPath("upc").description("UPC of beer"),
                                fieldWithPath("price").description("Price of the beer"),
                                fieldWithPath("quantityOnHand").description("Quantity on hand")
                        )));
    }

    @Test
    public void saveNewBeerTest() throws Exception {
        when(beerServices.saveNewBeer(any())).thenReturn(getValidBeerDto());

        BeerDto beerDto = BeerDto.builder()
                .beerName("Miss Quincy")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(BeerLoader.BEER_1_UPC)
                .price(BigDecimal.valueOf(2.75))
                .build();

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("creationDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("quantityOnHand").ignored(),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of the beer"),
                                fields.withPath("price").description("Price of the beer"),
                                fields.withPath("upc").description("UPC of the beer")
                        )));
    }

    @Test
    public void updateBeerTest() throws Exception {
        when(beerServices.updateBeer(any(), any())).thenReturn(getValidBeerDto());

        BeerDto beerDto = BeerDto.builder()
                .beerName("Miss Quincy")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(BeerLoader.BEER_2_UPC)
                .price(BigDecimal.valueOf(3.25))
                .build();

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isNoContent());
    }


    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("My beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.50"))
                .upc(BeerLoader.BEER_2_UPC)
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}
