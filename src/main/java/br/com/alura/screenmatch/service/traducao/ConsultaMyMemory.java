package br.com.alura.screenmatch.service.traducao;

import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConsultaMyMemory {

    private static final ConsumoApi consumoApi = new ConsumoApi();
    private static final ConverteDados converteDados = new ConverteDados();

    public static String traduzirString(String textoASerTraduzido) {

        String texto = URLEncoder.encode(textoASerTraduzido, StandardCharsets.UTF_8);
        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=en%7Cpt";
        String json = consumoApi.obterDados(url);
        DadosTraducao dadosTraducao = converteDados.obterDados(json, DadosTraducao.class);
        return dadosTraducao.respostaTraducao().textoTraduzido().trim();
    }

}
