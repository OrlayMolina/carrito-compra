package co.edu.uniquindio.carrito.configuration;

public class PlantillasEmailConfig {

    public static final String bodyReserva = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>QMD</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            background-color: #e0f7fa; /* Fondo azul clarito */\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "        }\n" +
            "        .header {\n" +
            "            background-color: #4C3181;\n" +
            "            color: white;\n" +
            "            text-align: center;\n" +
            "            padding: 20px;\n" +
            "            font-size: 24px;\n" +
            "            border-radius: 10px;\n" +
            "        }\n" +
            "        .container {\n" +
            "            text-align: center;\n" +
            "            margin: 50px auto;\n" +
            "            padding: 20px;\n" +
            "            width: 80%;\n" +
            "            max-width: 600px;\n" +
            "            background-color: white;\n" +
            "            border-radius: 10px;\n" +
            "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
            "        }\n" +
            "        .welcome-message {\n" +
            "            font-size: 18px;\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "        .activation-code {\n" +
            "            font-weight: bold;\n" +
            "            color: #19576e;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "    <div class=\"header\">\n" +
            "        Reserva en trámite!\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"container\">\n" +
            "        <p class=\"welcome-message\">[Nombres] [Apellidos], Gracias por hacer tu reserva, \"QMD\".</p>\n" +
            "    </div>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
}
