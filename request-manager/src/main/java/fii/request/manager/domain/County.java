package fii.request.manager.domain;

import java.util.HashMap;
import java.util.Map;

    public enum County {
        AB("AB", "Alba"),
        AR("AR", "Arad"),
        AG("AG", "Arges"),
        BC("BC", "Bacau"),
        BH("BH", "Bihor"),
        BN("BN", "Bistrita-Nasaud"),
        BT("BT", "Botosani"),
        BV("BV", "Brasov"),
        BR("BR", "Braila"),
        B("B", "Bucuresti"),
        BZ("BZ", "Buzau"),
        CS("CS", "Caras-Severin"),
        CL("CL", "Calarasi"),
        CJ("CJ", "Cluj"),
        CT("CT", "Constanta"),
        CV("CV", "Covasna"),
        DB("DB", "Dambovita"),
        DJ("DJ", "Dolj"),
        GL("GL", "Galati"),
        GR("GR", "Giurgiu"),
        GJ("GJ", "Gorj"),
        HR("HR", "Harghita"),
        HD("HD", "Hunedoara"),
        IL("IL", "Ilfov"),
        IS("IS", "Iasi"),
        IF("IF", "Ilfov"),
        MM("MM", "Maramures"),
        MH("MH", "Mehedinti"),
        MS("MS", "Mures"),
        NT("NT", "Neamt"),
        OT("OT", "Olt"),
        PH("PH", "Prahova"),
        SM("SM", "Satu Mare"),
        SJ("SJ", "Salaj"),
        SB("SB", "Sibiu"),
        SV("SV", "Suceava"),
        TR("TR", "Teleorman"),
        TM("TM", "Timis"),
        TL("TL", "Tulcea"),
        VS("VS", "Vaslui"),
        VL("VL", "Valcea"),
        VN("VN", "Vrancea");

        private static final Map<String, String> codeToNameMap = new HashMap<>();

        static {
            for (County country : County.values()) {
                codeToNameMap.put(country.countyCode, country.countyName);
            }
        }

        private final String countyCode;
        private final String countyName;

        private County(String countyCode, String countyName) {
            this.countyCode = countyCode;
            this.countyName = countyName;
        }

        public static String getCountyNameByCode(String code) {
            return codeToNameMap.get(code);
        }
    }
