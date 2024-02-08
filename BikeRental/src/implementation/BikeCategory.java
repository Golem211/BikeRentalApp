package implementation;

public class BikeCategory {
    private String denumire;
    private double pretInchiriatOra;

    public BikeCategory(String denumire, double pretInchiriatOra) {
        this.denumire = denumire;
        this.pretInchiriatOra = pretInchiriatOra;
    }

    public String getDenumire() {
        return denumire;
    }

    public double getPretInchiriatOra() {
        return pretInchiriatOra;
    }
}

