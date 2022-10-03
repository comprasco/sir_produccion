
//this.cuentasXCirculoBanco();

/*function cuentasXCirculoBanco() {
 document.getElementById('FormaPago').ACCION.value = 'CUENTAS_X_CIRCULO_BANCO';
 document.getElementById('FormaPago').submit();
 }*/

function removeRows() {
    alert("QUE PASA POR QUE NO BORRA 4");
    var parent = document.getElementById("idNuevaFormaPago");
    if (parent !== null) {
        while (parent.hasChildNodes()) {
            parent.removeChild(parent.firstChild);
        }

    }
    alert("QUE PASA POR QUE NO BORRA 4.5");
}


function cargarFormasPago(formasPago) {
    document.getElementById('FormaPago').ACCION.value = 'CARGAR_FORMAS_PAGO';
    document.getElementById('FORMAS_PAGO_MAP').value = formasPago;
    document.getElementById('FormaPago').submit();
}

function cargarCamposCaptura() {    
    document.getElementById('FormaPago').ACCION.value = 'CARGAR_CAMPOS_CAPTURA_X_FORMA';
    document.getElementById('FormaPago').submit();
}

function mostrarNuevaFormaPago(nuevo) {
//    document.getElementById('LLAVE_GENERAL_APLICACION').value = false;
    
    if(nuevo != true){
        document.getElementById('LLAVE_GENERAL_APLICACION').value = false;
        document.getElementById('idViejaFormaPago').style.display='block';
        document.getElementById('campoSaldoDocumento').style.display='block';  
        document.getElementById('botonValidar').style.display='block'; 
    }else{
        document.getElementById('LLAVE_GENERAL_APLICACION').value= true;
        document.getElementById('idViejaFormaPago').style.display='none';
        document.getElementById('campoSaldoDocumento').style.display='none';  
        document.getElementById('botonValidar').style.display='none'; 
    }
     
}

function validarFormaPago(formaPago) {
    alert("JAVASCRIPT validarFormaPago " + formaPago);
    if (formaPago == '2') {
        document.getElementById('FormaPago').ACCION.value = 'BUSCAR_CONSIGNACION';
        document.getElementById('FormaPago').submit();
    }
    if (formaPago == '3') {
        document.getElementById('FormaPago').ACCION.value = 'BUSCAR_CHEQUE';
        document.getElementById('FormaPago').submit();
    }
}

function validarGeneral() {
        document.getElementById('FormaPago').ACCION.value = 'BUSCAR_GENERAL';
        document.getElementById('FormaPago').submit();
}

function presentarInfo(){
        alert("JAVASCRIPT presentarInfo ");
        document.getElementById('idViejaFormaPago').style.display='block';
        document.getElementById('campoSaldoDocumento').style.display='block';  
        document.getElementById('botonValidar').style.display='block'; 
}

function cargarConfiguracionTiposPagos() {
    /*
     
     alert("JAVASCRIPT cargarConfiguracionTiposPagos ");
     if (document.getElementById('FORMA_TIPOS_PAGOS').value == '1' || document.getElementById('FORMA_TIPOS_PAGOS').value == '6') {
     document.getElementById('idViejaFormaPago').style.display = 'none';
     document.getElementById('idNuevaFormaPago').style.display = 'block';
     //2
     document.getElementById('Label_NuevaExisteSaldo').style.display = 'none';
     document.getElementById('Radio_FormaPagoNueva').style.display = 'none';
     //3
     document.getElementById('Label_BancoFranquicia').style.display = 'none';
     document.getElementById('COD_BANCO').style.display = 'none';
     document.getElementById('COD_BANCO_YA_REGISTRADA').style.display = 'none';
     document.getElementById('COD_BANCO_FRANQUICIA').style.display = 'none';
     //4
     document.getElementById('Label_NumeroDocumentoPago').style.display = 'none';
     document.getElementById('NUMERO_DOCUMENTO_PAGOS').style.display = 'none';
     //5
     document.getElementById('Label_ValorDocumento').style.display = 'none';
     document.getElementById('VALOR_DOCUMENTO_PAGOS').style.display = 'none';
     //6
     document.getElementById('Label_NumeroAprobacion').style.display = 'none';
     document.getElementById('NUMERO_APROBACION_PAGOS').style.display = 'none';
     //7
     document.getElementById('Label_FechaDocumento').style.display = 'none';
     document.getElementById('FECHA_DOCUMENTO_PAGOS').style.display = 'none';
     document.getElementById('ImagenFechaDocumentosPagos').style.display = 'none';
     //8
     document.getElementById('Label_ValorPagado').style.display = 'block';
     document.getElementById('VALOR_PAGAR_PAGOS').style.display = 'block';
     if (document.getElementById('FORMA_TIPOS_PAGOS').value == '1') {
     //Edgar Lora: Mantis: 0012422
     if (valorTotalLiquidado > 0) {
     document.getElementById('VALOR_PAGAR_PAGOS').value = '" + formateador2.format(valorTotalLiquidado)';
     } else {
     document.getElementById('VALOR_PAGAR_PAGOS').value = '';
     }
     } else {
     document.getElementById('VALOR_PAGAR_PAGOS').value = '';
     }
     } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '2' || document.getElementById('FORMA_TIPOS_PAGOS').value == '3') {
     document.getElementById('idViejaFormaPago').style.display = 'none';
     document.getElementById('idNuevaFormaPago').style.display = 'block';
     //2
     document.getElementById('Label_NuevaExisteSaldo').style.display = 'block';
     document.getElementById('Radio_FormaPagoNueva').style.display = 'block';
     //Edgar Lora: Mantis: 0012422
     document.getElementById('FORMA_PAGO_NUEVA').checked = 'true';
     document.getElementById('FORMA_PAGO_EXISTENTE').checked = 'false';
     //3
     document.getElementById('Label_BancoFranquicia').style.display = 'block';
     document.getElementById('COD_BANCO_YA_REGISTRADA').style.display = 'none';
     document.getElementById('COD_BANCO').style.display = 'block';
     document.getElementById('COD_BANCO_FRANQUICIA').style.display = 'none';
     //4
     document.getElementById('Label_NumeroDocumentoPago').style.display = 'block';
     document.getElementById('NUMERO_DOCUMENTO_PAGOS').style.display = 'block';
     //5
     document.getElementById('Label_ValorDocumento').style.display = 'block';
     document.getElementById('VALOR_DOCUMENTO_PAGOS').style.display = 'block';
     //6
     document.getElementById('Label_NumeroAprobacion').style.display = 'none';
     document.getElementById('NUMERO_APROBACION_PAGOS').style.display = 'none';
     //7
     document.getElementById('Label_FechaDocumento').style.display = 'block';
     document.getElementById('FECHA_DOCUMENTO_PAGOS').style.display = 'block';
     document.getElementById('ImagenFechaDocumentosPagos').style.display = 'block';
     //8
     document.getElementById('Label_ValorPagado').style.display = 'block';
     document.getElementById('VALOR_PAGAR_PAGOS').style.display = 'block';
     //Edgar Lora: Mantis: 0012422
     document.getElementById('VALOR_PAGAR_PAGOS').value = '';
     } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '9') {
     document.getElementById('idViejaFormaPago').style.display = 'none';
     document.getElementById('idNuevaFormaPago').style.display = 'block';
     //2
     document.getElementById('Label_NuevaExisteSaldo').style.display = 'none';
     document.getElementById('Radio_FormaPagoNueva').style.display = 'none';
     //3
     //Edgar Lora: Mantis: 0012422
     document.getElementById('Label_BancoFranquicia').style.display = 'block';
     document.getElementById('COD_BANCO').style.display = 'none';
     document.getElementById('COD_BANCO_YA_REGISTRADA').style.display = 'none';
     document.getElementById('COD_BANCO_FRANQUICIA').style.display = 'block';            //4
     document.getElementById('Label_NumeroDocumentoPago').style.display = 'none';
     document.getElementById('NUMERO_DOCUMENTO_PAGOS').style.display = 'none';
     //5
     document.getElementById('Label_ValorDocumento').style.display = 'none';
     document.getElementById('VALOR_DOCUMENTO_PAGOS').style.display = 'none';
     //6
     document.getElementById('Label_NumeroAprobacion').style.display = 'block';
     document.getElementById('NUMERO_APROBACION_PAGOS').style.display = 'block';
     //7
     document.getElementById('Label_FechaDocumento').style.display = 'block';
     document.getElementById('FECHA_DOCUMENTO_PAGOS').style.display = 'block';
     document.getElementById('ImagenFechaDocumentosPagos').style.display = 'block';
     //8
     document.getElementById('Label_ValorPagado').style.display = 'block';
     document.getElementById('VALOR_PAGAR_PAGOS').style.display = 'block';
     //Edgar Lora: Mantis: 0012422
     document.getElementById('VALOR_PAGAR_PAGOS').value = '';
     } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '11' || document.getElementById('FORMA_TIPOS_PAGOS').value == '12') {
     document.getElementById('idViejaFormaPago').style.display = 'none';
     document.getElementById('idNuevaFormaPago').style.display = 'block';
     //2
     document.getElementById('Label_NuevaExisteSaldo').style.display = 'none';
     document.getElementById('Radio_FormaPagoNueva').style.display = 'none';
     //3
     document.getElementById('Label_BancoFranquicia').style.display = 'block';
     document.getElementById('COD_BANCO').style.display = 'none';
     document.getElementById('COD_BANCO_YA_REGISTRADA').style.display = 'none';
     document.getElementById('COD_BANCO_FRANQUICIA').style.display = 'block';
     //4
     document.getElementById('Label_NumeroDocumentoPago').style.display = 'block';
     document.getElementById('NUMERO_DOCUMENTO_PAGOS').style.display = 'block';
     //5
     document.getElementById('Label_ValorDocumento').style.display = 'none';
     document.getElementById('VALOR_DOCUMENTO_PAGOS').style.display = 'none';
     //6
     document.getElementById('Label_NumeroAprobacion').style.display = 'block';
     document.getElementById('NUMERO_APROBACION_PAGOS').style.display = 'block';
     //7
     document.getElementById('Label_FechaDocumento').style.display = 'block';
     document.getElementById('FECHA_DOCUMENTO_PAGOS').style.display = 'block';
     document.getElementById('ImagenFechaDocumentosPagos').style.display = 'block';
     //8
     document.getElementById('Label_ValorPagado').style.display = 'block';
     document.getElementById('VALOR_PAGAR_PAGOS').style.display = 'block';
     //Edgar Lora: Mantis: 0012422
     document.getElementById('VALOR_PAGAR_PAGOS').value = '';
     } else if (document.getElementById('FORMA_TIPOS_PAGOS').value == '7') {
     document.getElementById('idViejaFormaPago').style.display = 'none';
     document.getElementById('idNuevaFormaPago').style.display = 'block';
     //2
     document.getElementById('Label_NuevaExisteSaldo').style.display = 'none';
     document.getElementById('Radio_FormaPagoNueva').style.display = 'none';
     //3
     document.getElementById('Label_BancoFranquicia').style.display = 'none';
     document.getElementById('COD_BANCO').style.display = 'none';
     document.getElementById('COD_BANCO_YA_REGISTRADA').style.display = 'none';
     document.getElementById('COD_BANCO_FRANQUICIA').style.display = 'none';
     //4
     document.getElementById('Label_NumeroDocumentoPago').style.display = 'block';
     document.getElementById('NUMERO_DOCUMENTO_PAGOS').style.display = 'block';
     document.getElementById('NUMERO_DOCUMENTO_PAGOS').value = '';
     //5
     document.getElementById('Label_ValorDocumento').style.display = 'none';
     document.getElementById('VALOR_DOCUMENTO_PAGOS').style.display = 'none';
     //6
     document.getElementById('Label_NumeroAprobacion').style.display = 'none';
     document.getElementById('NUMERO_APROBACION_PAGOS').style.display = 'none';
     //7
     document.getElementById('Label_FechaDocumento').style.display = 'block';
     document.getElementById('FECHA_DOCUMENTO_PAGOS').style.display = 'block';
     document.getElementById('ImagenFechaDocumentosPagos').style.display = 'block';
     //8
     document.getElementById('Label_ValorPagado').style.display = 'block';
     document.getElementById('VALOR_PAGAR_PAGOS').style.display = 'block';
     //Edgar Lora: Mantis: 0012422
     document.getElementById('VALOR_PAGAR_PAGOS').value = '';
     }*/
}

function limpiarCampos() {
    /*alert("JAVASCRIPT limpiarCampos ");
     if (document.getElementById('FORMA_TIPOS_PAGOS').value != '1') {
     document.getElementById('COD_BANCO').value = 'SIN_SELECCIONAR';
     document.getElementById('COD_BANCO_YA_REGISTRADA').value = '';
     document.getElementById('COD_BANCO_FRANQUICIA').value = '';
     document.getElementById('NUMERO_DOCUMENTO_PAGOS').value = '';
     document.getElementById('VALOR_DOCUMENTO_PAGOS').value = '';
     document.getElementById('NUMERO_APROBACION_PAGOS').value = '';
     document.getElementById('FECHA_DOCUMENTO_PAGOS').value = '';
     document.getElementById('VALOR_PAGAR_PAGOS').value = '';
     }*/
}

function soloLetras(e) {
    //alert("JAVASCRIPT soloLetras " + e);
    var key = e.keyCode || e.which;
    var tecla = String.fromCharCode(key).toLowerCase();
    var letras = 'abcdefghijklmnopqrstuvwxyz1234567890';
    var especiales = [2000, 2001];
    var tecla_especial = false;
    for (var i in especiales) {
        if (key == especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if (letras.indexOf(tecla) == -1 && !tecla_especial)
        return false;
}

function limpia() {
    //alert("JAVASCRIPT limpia ");
    var val = document.getElementById('NUMERO_DOCUMENTO_PAGOS').value;
    var tam = val.length;
    var letras = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
    var buffer = val;
    for (var i = 0; i < tam; i++) {
        var l = val.substring(i, i + 1);
        var is = letras.indexOf(l);
        if (is == -1) {
            buffer = buffer.replace(l, '');
        }
    }
    document.getElementById('NUMERO_DOCUMENTO_PAGOS').value = buffer;
}

