package co.com.sofka.sbdcpc.usecase.causante;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObtenerMesadaUseCase {
    public Double obtenerMesada(Double salario, Integer meses){
        Double mesadaBeneficiario;

        if (meses <= 36) {
            mesadaBeneficiario = salario * 0.5;
        } else if (meses >= 37 && salario <= 60) {
            mesadaBeneficiario = salario * 0.75;
        } else if (salario <= 1500000) {
            mesadaBeneficiario = salario * 0.75 + (meses * 2000);
        } else {
            mesadaBeneficiario = salario * 0.8 + (meses * 1000);
        }

        return mesadaBeneficiario;
    }
}