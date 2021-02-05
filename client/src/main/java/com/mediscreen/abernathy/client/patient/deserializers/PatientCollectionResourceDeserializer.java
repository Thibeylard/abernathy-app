package com.mediscreen.abernathy.client.patient.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mediscreen.abernathy.client.patient.dtos.PatientCollectionResourceDTO;
import com.mediscreen.abernathy.client.patient.dtos.PatientItemResourceDTO;
import com.mediscreen.abernathy.client.patient.dtos.ResourceLinksDTO;
import com.mediscreen.abernathy.client.patient.dtos.ResourcePageDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PatientCollectionResourceDeserializer extends StdDeserializer<PatientCollectionResourceDTO> {

    private ObjectMapper mapper;

    public PatientCollectionResourceDeserializer(ObjectMapper mapper) {
        super(PatientCollectionResourceDTO.class);
        this.mapper = mapper;
    }

    /**
     * Method that can be called to ask implementation to deserialize
     * JSON content into the value type this serializer handles.
     * Returned instance is to be constructed by method itself.
     * <p>
     * Pre-condition for this method is that the parser points to the
     * first event that is part of value to deserializer (and which
     * is never JSON 'null' literal, more on this below): for simple
     * types it may be the only value; and for structured types the
     * Object start marker or a FIELD_NAME.
     * </p>
     * <p>
     * The two possible input conditions for structured types result
     * from polymorphism via fields. In the ordinary case, Jackson
     * calls this method when it has encountered an OBJECT_START,
     * and the method implementation must advance to the next token to
     * see the first field name. If the application configures
     * polymorphism via a field, then the object looks like the following.
     * <pre>
     *      {
     *          "@class": "class name",
     *          ...
     *      }
     *  </pre>
     * Jackson consumes the two tokens (the <tt>@class</tt> field name
     * and its value) in order to learn the class and select the deserializer.
     * Thus, the stream is pointing to the FIELD_NAME for the first field
     * after the @class. Thus, if you want your method to work correctly
     * both with and without polymorphism, you must begin your method with:
     * <pre>
     *       if (p.currentToken() == JsonToken.START_OBJECT) {
     *         p.nextToken();
     *       }
     *  </pre>
     * This results in the stream pointing to the field name, so that
     * the two conditions align.
     * <p>
     * Post-condition is that the parser will point to the last
     * event that is part of deserialized value (or in case deserialization
     * fails, event that was not recognized or usable, which may be
     * the same event as the one it pointed to upon call).
     * <p>
     * Note that this method is never called for JSON null literal,
     * and thus deserializers need (and should) not check for it.
     *
     * @param p    Parsed used for reading JSON content
     * @param ctxt Context that can be used to access information about
     *             this deserialization activity.
     * @return Deserialized value
     */
    @Override
    public PatientCollectionResourceDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode tree = p.getCodec().readTree(p);/*
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.set("_embedded", (JsonNode) p.getCodec().readTree(p).get("_embedded"));
        objectNode.set("_links", (JsonNode) p.getCodec().readTree(p).get("_links"));
        objectNode.set("page", (JsonNode) p.getCodec().readTree(p).get("page"));*/
        boolean empty = tree.isEmpty();
        boolean nullTree = tree.isNull();

        List<PatientItemResourceDTO> items = new ArrayList<>();
        String links = tree.at("/").asText();
        JsonNode embeddedNode = (JsonNode) tree.get(0);
        JsonNode linksNode = (JsonNode) tree.get(1);
        JsonNode pageNode = (JsonNode) tree.get(2);

        List<String> keys = new ArrayList<>();

        tree.fields().forEachRemaining(n -> keys.add(n.getKey()));
        ResourceLinksDTO resourceLinksDTO = mapper.readValue(links, ResourceLinksDTO.class);
        ResourcePageDTO pageDTO = mapper.readValue(tree.at("/page").toString(), ResourcePageDTO.class);


        /*patients.forEach(
                item -> items.add(mapper.convertValue(item, PatientItemResourceDTO.class))
        );*/

        return new PatientCollectionResourceDTO(
                items,
                resourceLinksDTO,
                pageDTO);
    }
}
