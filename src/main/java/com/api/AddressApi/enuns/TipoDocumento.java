package com.api.AddressApi.enuns;

public enum TipoDocumento {
    PF(0),
    PJ(1);

    private int code;

    TipoDocumento(int code)
    {
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static TipoDocumento valueOf(int code){
        for(TipoDocumento value : TipoDocumento.values()){
            if(code == value.getCode()) {
                return value;
            }
        }
        throw new IllegalArgumentException("Tipo de documento inválido!");
    }
}
