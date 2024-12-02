package com.graphhopper.sfo.rgeocode;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.ev.BooleanEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.EnumEncodedValue;
import com.graphhopper.routing.ev.IntEncodedValue;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.search.KVStorage;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.util.EdgeIteratorState;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("reverse-geocode")
public class ReverseGeocodeResource {
    private final LocationIndex locationIndex;
    private final EncodingManager encodingManager;

    @Inject
    public ReverseGeocodeResource(GraphHopper graphhopper, EncodingManager encodingManager) {
        this.locationIndex = graphhopper.getLocationIndex();
        this.encodingManager = encodingManager;
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(SnapRequest requestBody) {
        // possible validations must implement later


        // we can implement edge filter, for example do not snap on footways or steps
        EdgeIteratorState edge = locationIndex
                .findClosest(requestBody.getLat(), requestBody.getLon(), EdgeFilter.ALL_EDGES)
                .getClosestEdge();

        // based on project demands a class for response must be created later
        Map<String, String> result = new HashMap<>(3);
        for (Map.Entry<String, KVStorage.KValue> e : edge.getKeyValues().entrySet()) {
            result.put(e.getKey(), e.getValue().toString());
        }

        encodingManager.getEncodedValues().forEach(ev -> {
            if (ev instanceof EnumEncodedValue)
                result.put(ev.getName(), edge.get((EnumEncodedValue) ev).toString() + (ev.isStoreTwoDirections() ? " | " + edge.getReverse((EnumEncodedValue) ev).toString() : ""));
            else if (ev instanceof DecimalEncodedValue)
                result.put(ev.getName(), edge.get((DecimalEncodedValue) ev) + (ev.isStoreTwoDirections() ? " | " + edge.getReverse((DecimalEncodedValue) ev) : ""));
            else if (ev instanceof BooleanEncodedValue)
                result.put(ev.getName(), edge.get((BooleanEncodedValue) ev) + (ev.isStoreTwoDirections() ? " | " + edge.getReverse((BooleanEncodedValue) ev) : ""));
            else if (ev instanceof IntEncodedValue)
                result.put(ev.getName(), edge.get((IntEncodedValue) ev) + (ev.isStoreTwoDirections() ? " | " + edge.getReverse((IntEncodedValue) ev) : ""));
        });

        // possible exceptions must be added later
        return Response.status(Response.Status.OK)
                .entity(result)
                .build();
    }
}
