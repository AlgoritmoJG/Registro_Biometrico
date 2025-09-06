package com.example.biometric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryCityUtils {
    private static final Map<String, List<String>> countryCities = new HashMap<>();

    static {
        // Países y sus ciudades principales
        List<String> mexicoCities = new ArrayList<>();
        mexicoCities.add("Ciudad de México");
        mexicoCities.add("Guadalajara");
        mexicoCities.add("Monterrey");
        mexicoCities.add("Puebla");
        mexicoCities.add("Tijuana");
        mexicoCities.add("León");
        mexicoCities.add("Juárez");
        mexicoCities.add("Zapopan");
        mexicoCities.add("Nezahualcóyotl");
        mexicoCities.add("Chihuahua");
        countryCities.put("México", mexicoCities);

        List<String> usaCities = new ArrayList<>();
        usaCities.add("Nueva York");
        usaCities.add("Los Ángeles");
        usaCities.add("Chicago");
        usaCities.add("Houston");
        usaCities.add("Phoenix");
        usaCities.add("Filadelfia");
        usaCities.add("San Antonio");
        usaCities.add("San Diego");
        usaCities.add("Dallas");
        usaCities.add("San José");
        countryCities.put("Estados Unidos", usaCities);

        List<String> canadaCities = new ArrayList<>();
        canadaCities.add("Toronto");
        canadaCities.add("Montreal");
        canadaCities.add("Vancouver");
        canadaCities.add("Calgary");
        canadaCities.add("Edmonton");
        canadaCities.add("Ottawa");
        canadaCities.add("Winnipeg");
        canadaCities.add("Quebec");
        canadaCities.add("Hamilton");
        canadaCities.add("Kitchener");
        countryCities.put("Canadá", canadaCities);

        List<String> spainCities = new ArrayList<>();
        spainCities.add("Madrid");
        spainCities.add("Barcelona");
        spainCities.add("Valencia");
        spainCities.add("Sevilla");
        spainCities.add("Zaragoza");
        spainCities.add("Málaga");
        spainCities.add("Murcia");
        spainCities.add("Palma");
        spainCities.add("Las Palmas");
        spainCities.add("Bilbao");
        countryCities.put("España", spainCities);

        List<String> franceCities = new ArrayList<>();
        franceCities.add("París");
        franceCities.add("Marsella");
        franceCities.add("Lyon");
        franceCities.add("Toulouse");
        franceCities.add("Niza");
        franceCities.add("Nantes");
        franceCities.add("Estrasburgo");
        franceCities.add("Montpellier");
        franceCities.add("Burdeos");
        franceCities.add("Lille");
        countryCities.put("Francia", franceCities);

        List<String> germanyCities = new ArrayList<>();
        germanyCities.add("Berlín");
        germanyCities.add("Hamburgo");
        germanyCities.add("Múnich");
        germanyCities.add("Colonia");
        germanyCities.add("Fráncfort");
        germanyCities.add("Stuttgart");
        germanyCities.add("Düsseldorf");
        germanyCities.add("Dortmund");
        germanyCities.add("Essen");
        germanyCities.add("Leipzig");
        countryCities.put("Alemania", germanyCities);

        List<String> italyCities = new ArrayList<>();
        italyCities.add("Roma");
        italyCities.add("Milán");
        italyCities.add("Nápoles");
        italyCities.add("Turín");
        italyCities.add("Palermo");
        italyCities.add("Génova");
        italyCities.add("Bolonia");
        italyCities.add("Florencia");
        italyCities.add("Bari");
        italyCities.add("Catania");
        countryCities.put("Italia", italyCities);

        List<String> ukCities = new ArrayList<>();
        ukCities.add("Londres");
        ukCities.add("Birmingham");
        ukCities.add("Manchester");
        ukCities.add("Glasgow");
        ukCities.add("Liverpool");
        ukCities.add("Leeds");
        ukCities.add("Sheffield");
        ukCities.add("Edimburgo");
        ukCities.add("Bristol");
        ukCities.add("Leicester");
        countryCities.put("Reino Unido", ukCities);

        List<String> japanCities = new ArrayList<>();
        japanCities.add("Tokio");
        japanCities.add("Yokohama");
        japanCities.add("Osaka");
        japanCities.add("Nagoya");
        japanCities.add("Sapporo");
        japanCities.add("Fukuoka");
        japanCities.add("Kobe");
        japanCities.add("Kawasaki");
        japanCities.add("Kyoto");
        japanCities.add("Saitama");
        countryCities.put("Japón", japanCities);

        List<String> chinaCities = new ArrayList<>();
        chinaCities.add("Shanghái");
        chinaCities.add("Pekín");
        chinaCities.add("Chongqing");
        chinaCities.add("Tianjin");
        chinaCities.add("Guangzhou");
        chinaCities.add("Shenzhen");
        chinaCities.add("Wuhan");
        chinaCities.add("Dongguan");
        chinaCities.add("Chengdu");
        chinaCities.add("Nanjing");
        countryCities.put("China", chinaCities);

        List<String> brazilCities = new ArrayList<>();
        brazilCities.add("São Paulo");
        brazilCities.add("Río de Janeiro");
        brazilCities.add("Brasilia");
        brazilCities.add("Salvador");
        brazilCities.add("Fortaleza");
        brazilCities.add("Belo Horizonte");
        brazilCities.add("Manaus");
        brazilCities.add("Curitiba");
        brazilCities.add("Recife");
        brazilCities.add("Porto Alegre");
        countryCities.put("Brasil", brazilCities);

        List<String> argentinaCities = new ArrayList<>();
        argentinaCities.add("Buenos Aires");
        argentinaCities.add("Córdoba");
        argentinaCities.add("Rosario");
        argentinaCities.add("Mendoza");
        argentinaCities.add("La Plata");
        argentinaCities.add("Tucumán");
        argentinaCities.add("Mar del Plata");
        argentinaCities.add("Salta");
        argentinaCities.add("Santa Fe");
        argentinaCities.add("San Juan");
        countryCities.put("Argentina", argentinaCities);

        List<String> colombiaCities = new ArrayList<>();
        colombiaCities.add("Bogotá");
        colombiaCities.add("Medellín");
        colombiaCities.add("Cali");
        colombiaCities.add("Barranquilla");
        colombiaCities.add("Cartagena");
        colombiaCities.add("Cúcuta");
        colombiaCities.add("Bucaramanga");
        colombiaCities.add("Pereira");
        colombiaCities.add("Santa Marta");
        colombiaCities.add("Ibagué");
        countryCities.put("Colombia", colombiaCities);

        List<String> chileCities = new ArrayList<>();
        chileCities.add("Santiago");
        chileCities.add("Valparaíso");
        chileCities.add("Concepción");
        chileCities.add("La Serena");
        chileCities.add("Antofagasta");
        chileCities.add("Temuco");
        chileCities.add("Rancagua");
        chileCities.add("Talca");
        chileCities.add("Arica");
        chileCities.add("Chillán");
        countryCities.put("Chile", chileCities);

        List<String> peruCities = new ArrayList<>();
        peruCities.add("Lima");
        peruCities.add("Arequipa");
        peruCities.add("Trujillo");
        peruCities.add("Chiclayo");
        peruCities.add("Piura");
        peruCities.add("Iquitos");
        peruCities.add("Cusco");
        peruCities.add("Chimbote");
        peruCities.add("Huancayo");
        peruCities.add("Tacna");
        countryCities.put("Perú", peruCities);
    }

    public static List<String> getCountries() {
        return new ArrayList<>(countryCities.keySet());
    }

    public static List<String> getCitiesForCountry(String country) {
        return countryCities.getOrDefault(country, new ArrayList<>());
    }
}
