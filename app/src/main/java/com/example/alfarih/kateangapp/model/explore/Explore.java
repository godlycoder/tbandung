package com.example.alfarih.kateangapp.model.explore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfarih on 14/11/17.
 */

public class Explore {
    private Meta meta;
    private Response response;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Meta getMeta() {
        return meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
