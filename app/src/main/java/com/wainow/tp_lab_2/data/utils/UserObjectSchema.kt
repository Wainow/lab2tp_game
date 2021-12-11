package com.wainow.tp_lab_2.data.utils

object UserObjectSchema {
    fun get() =
        "{\n" +
                "  \"\$schema\": \"http://json-schema.org/draft-06/schema#\",\n" +
                "  \"\$ref\": \"#/definitions/Game\",\n" +
                "  \"definitions\": {\n" +
                "    \"Game\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"additionalProperties\": false,\n" +
                "      \"properties\": {\n" +
                "        \"user1\": {\n" +
                "          \"\$ref\": \"#/definitions/User\"\n" +
                "        },\n" +
                "        \"user2\": {\n" +
                "          \"\$ref\": \"#/definitions/User\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"required\": [\n" +
                "        \"user1\",\n" +
                "        \"user2\"\n" +
                "      ],\n" +
                "      \"title\": \"Game\"\n" +
                "    },\n" +
                "    \"User\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"additionalProperties\": false,\n" +
                "      \"properties\": {\n" +
                "        \"id\": {\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"currentNumber\": {\n" +
                "          \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"availableOperations\": {\n" +
                "          \"type\": \"array\",\n" +
                "          \"items\": {\n" +
                "            \"type\": \"string\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"availableNumbers\": {\n" +
                "          \"type\": \"array\",\n" +
                "          \"items\": {\n" +
                "            \"type\": \"integer\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"result\": {\n" +
                "          \"type\": \"string\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"required\": [\n" +
                "        \"availableNumbers\",\n" +
                "        \"availableOperations\",\n" +
                "        \"currentNumber\",\n" +
                "        \"id\",\n" +
                "        \"result\"\n" +
                "      ],\n" +
                "      \"title\": \"User\"\n" +
                "    }\n" +
                "  }\n" +
                "}"
}