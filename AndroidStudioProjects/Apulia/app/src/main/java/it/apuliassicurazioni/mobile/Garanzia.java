package it.apuliassicurazioni.mobile;

class Garanzia {
    double imponibile;
    int frazionamento;

    private double coeff;

    Garanzia(double imp, int fraz) {
        this( imp, fraz, 0.025 );
    }

    public Garanzia(double imp, int fraz, double coeffImposte) {
        imponibile = imp;
        frazionamento = fraz;
        coeff = coeffImposte;
    }

    private double imposte() {
        return Utils.arrotonda( imponibile * coeff );
    }

    private double imposteRateSuccessive() {
        return Utils.arrotonda( (imponibileRateSuccessive() + interessi()) * coeff );
    }

    private double imponibileRateSuccessive() {
        switch (frazionamento) {
            case 1:
                return Utils.arrotonda( imponibile / 4.0 );
            case 2:
                return Utils.arrotonda( imponibile / 2.0 );
            default:
                return imponibile;
        }
    }

    double premio() {
        return imponibile + imposte();
    }

    double interessi() {
        switch (frazionamento) {
            case 1:
                return imponibileRateSuccessive() * .03;
            case 2:
                return imponibileRateSuccessive() * .02;
            default:
                return 0;
        }
    }

    double rata() {
        return imponibileRateSuccessive() + interessi() + imposteRateSuccessive();
    }
}
