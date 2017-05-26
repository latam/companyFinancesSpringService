package pl.mlata.reports;

import java.text.DecimalFormat;

public class DoubleToTextConverter {
    public static String convert(Double value) {
        String outputText = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String valueText = decimalFormat.format(value);
        String[] valueFractions = valueText.split(",");
        Long number = Long.parseLong(valueFractions[0]);
        Long fraction = Long.parseLong(valueFractions[1]);
        outputText = partialConvert(number) + "PLN";
        if(fraction != 0L)
            outputText += " " + fraction.toString() + "/100";

        if(!outputText.isEmpty())
            outputText = outputText.substring(0,1).toUpperCase() + outputText.substring(1);
        return outputText;
    }

    private static String partialConvert(Long value) {
        String[] unitsText = {"", "jeden ", "dwa ", "trzy ", "cztery ",
                "pięć ", "sześć ", "siedem ", "osiem ", "dziewięć ",};
        String[] teensText = {"", "jedenaście ", "dwanaście ", "trzynaście ",
                "czternaście ", "piętnaście ", "szesnaście ", "siedemnaście ",
                "osiemnaście ", "dziewiętnaście ",};
        String[] tensText = {"", "dziesięć ", "dwadzieścia ",
                "trzydzieści ", "czterdzieści ", "pięćdziesiąt ",
                "sześćdziesiąt ", "siedemdziesiąt ", "osiemdziesiąt ",
                "dziewięćdziesiąt ",};
        String[] hundredsText = {"", "sto ", "dwieście ", "trzysta ", "czterysta ",
                "pięćset ", "sześćset ", "siedemset ", "osiemset ",
                "dziewięćset ",};
        String[][] groupsText = {{"", "", ""},
                {"tysiąc ", "tysiące ", "tysięcy "},
                {"milion ", "miliony ", "milionów "},
                {"miliard ", "miliardy ", "miliardów "},
                {"bilion ", "biliony ", "bilionów "},
                {"biliard ", "biliardy ", "biliardów "},
                {"trylion ", "tryliony ", "trylionów "},};

        long units = 0, teens = 0, tens = 0, hundreds = 0, groups = 0, endings = 0;
        String outputText = "";
        String sign = "";

        if (value < 0) {
            sign = "minus ";
            value = -value;
        }

        if (value == 0) {
            sign = "zero";
        }

        while (value != 0) {
            hundreds = value % 1000 / 100;
            tens = value % 100 / 10;
            units = value % 10;

            if (tens == 1 & units > 0) {
                teens = units;
                tens = 0;
                units = 0;
            }
            else {
                teens = 0;
            }

            if (units == 1 & hundreds + tens + teens == 0) {
                endings = 0;
                if (hundreds + tens == 0 && groups > 0) {
                    units = 0;
                    outputText = groupsText[(int) groups][(int) endings] + outputText;
                }
            }
            else if (units == 2) {
                endings = 1;
            }
            else if (units == 3) {
                endings = 1;
            }
            else if (units == 4) {
                endings = 1;
            }
            else {
                endings = 2;
            }

            if (hundreds + tens + teens + units > 0) {
                outputText = hundredsText[(int) hundreds] + tensText[(int) tens] + teensText[(int) teens]
                        + unitsText[(int) units] + groupsText[(int) groups][(int) endings] + outputText;
            }

            value = value / 1000;
            groups = groups + 1;
        }

        outputText = sign + outputText;
        return outputText;
    }
}
