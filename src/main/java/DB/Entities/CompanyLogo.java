package main.java.DB.Entities;

public class CompanyLogo {
    private String companyName;
    private String logoUrl;

    public CompanyLogo(String companyName, String logoUrl) {
        this.companyName = companyName;
        this.logoUrl = logoUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
