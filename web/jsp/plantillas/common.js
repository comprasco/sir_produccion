function limpiarDatos(s) {
    try {
        document.getElementById(s).value = '';
        document.getElementById('ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA').value = '';
        document.getElementById('ANOTACION_OFICINA_DOCUMENTO_NUM').value = '';
        document.getElementById('ANOTACION_OFICINA_DOCUMENTO_TIPO').value = '';
    } catch (e) {
    }
}

function limpiarDatosOficina(s, oficinaId, oficinaNum, oficinaTipo) {
    try {
        document.getElementById(s).value = '';
        document.getElementById(oficinaId).value = '';
        document.getElementById(oficinaNum).value = '';
        document.getElementById(oficinaTipo).value = '';
    } catch (e) {
    }
}

function locacionVereda(nombre, valor, dimensiones, mostrarvereda) {
    document.getElementById('id_depto').value = valor + "_ID_DEPTO";
    document.getElementById('nom_Depto').value = valor + "_NOM_DEPTO";
    document.getElementById('id_munic').value = valor + "_ID_MUNIC";
    document.getElementById('nom_munic').value = valor + "_NOM_MUNIC";
    document.getElementById('id_vereda').value = valor + "_ID_VEREDA";
    document.getElementById('nom_vereda').value = valor + "_NOM_VEREDA";
    popup = window.open(nombre + '&MOSTRAR_VEREDA=MOSTRAR_VEREDA', valor, dimensiones);
    popup.focus();
}

function validarNumerico(id) {
    if (isNaN(document.getElementById(id).value)) {
        alert('Ha ocurrido el siguiente error: El valor debe ser numérico.');
        document.getElementById(id).focus();
    }
}

function validarNumericoRequerido(id) {
    if (isNaN(document.getElementById(id).value) || document.getElementById(id).value == '') {
        alert('Ha ocurrido el siguiente error: El valor es obligatorio y debe ser numérico.');
        document.getElementById(id).focus();
    }
}

function validarNumericoRequeridoValorDefecto(id, defaultValue) {
    if (isNaN(document.getElementById(id).value) || document.getElementById(id).value == '') {
        alert('Ha ocurrido el siguiente error: El valor es obligatorio y debe ser numérico.');
        document.getElementById(id).value = defaultValue;
        document.getElementById(id).focus();
    }
}

function validarRequerido(id) {
    if (document.getElementById(id).value == '') {
        alert('Ha ocurrido el siguiente error: El valor es obligatorio.');
        document.getElementById(id).focus();
    }
}

function refrescar() {
    document.location.reload();
}

function setAllCheckBoxes(FormName, FieldName, CheckValue)
{
    if (!document.forms[FormName])
        return;
    var objCheckBoxes = document.forms[FormName].elements[FieldName];
    if (!objCheckBoxes)
        return;
    var countCheckBoxes = objCheckBoxes.length;
    if (!countCheckBoxes)
        objCheckBoxes.checked = CheckValue;
    else
        // set the check value for all check boxes
        for (var i = 0; i < countCheckBoxes; i++)
            objCheckBoxes[i].checked = CheckValue;
}


function campoactual(id) {
    if (document.getElementById("ultimo_campo_editado") != null) {
        document.getElementById("ultimo_campo_editado").value = id;
    }
}

function writeCookie(cookieName, cookieValue) {
    var today = new Date();
    var expire = new Date();
    expire.setTime(today.getTime() - 1);
    document.cookie = cookieName + "=" + escape(cookieValue) + ";expires=" + expire.toGMTString();
}

function readCookie(cookieName) {
    var theCookie = "" + document.cookie;
    var ind = theCookie.indexOf(cookieName);
    if (ind == -1 || cookieName == "")
        return "";
    var ind1 = theCookie.indexOf(';', ind);
    if (ind1 == -1)
        ind1 = theCookie.length;
    return unescape(theCookie.substring(ind + cookieName.length + 1, ind1));
}


function findObj(theObj, theDoc)
{
    var p, i, foundObj;

    if (!theDoc)
        theDoc = document;
    if ((p = theObj.indexOf("?")) > 0 && parent.frames.length)
    {
        theDoc = parent.frames[theObj.substring(p + 1)].document;
        theObj = theObj.substring(0, p);
    }
    if (!(foundObj = theDoc[theObj]) && theDoc.all)
        foundObj = theDoc.all[theObj];
    for (i = 0; !foundObj && i < theDoc.forms.length; i++)
        foundObj = theDoc.forms[i][theObj];
    for (i = 0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
        foundObj = findObj(theObj, theDoc.layers[i].document);
    if (!foundObj && document.getElementById)
        foundObj = document.getElementById(theObj);

    return foundObj;
}

function validarCaracter(evt) {
    if (!evt) {
        // grab IE event object
        evt = window.event
    } else if (!evt.keyCode) {
        // grab NN4 event info
        evt.keyCode = evt.which
    }

    // Si es backspace o suprimir, acéptelo
    if (evt.keyCode == 8 || evt.keyCode == 46)
        return true;

    // Los números del pad numérico tienen
    // códigos distintos a los normales
    if (evt.keyCode >= 95 && evt.keyCode <= 105)
        return true;

    // Obtenga el caracter que representa al keyCode
    numString = String.fromCharCode(evt.keyCode);
    num = parseInt(numString);

    // Si no es un número, se retorna false, de lo contario,
    // se retorna true.
    return !isNaN(num);
}

function reescribirValor(valor, id) {

    /* JAlcaza caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
     * Redondeo del valor
     */
    var round = valor / 100;
    round = Math.round(round);
    round = round * 100;
    var my_str = new String(round);
    var miles = 1;
    if (my_str.indexOf(".") == -1) {
        if (my_str.indexOf(",") == -1) {
            var nStr = "";
            for (var i = 1; i <= my_str.length; i++) {
                var desde = my_str.length - i * 3;
                var hasta = my_str.length - (3 * (i - 1));
                var temp = my_str.slice(desde, hasta);
                var separador = "";
                if (hasta > 3) {
                    if (miles == 1) {
                        miles = 0;
                        separador = ",";
                    } else {
                        miles = 1
                        separador = ",";
                    }
                    nStr = separador + temp + nStr;
                } else {
                    if (hasta > 0) {
                        temp = my_str.slice(0, hasta);
                        nStr = temp + nStr;
                    }
                }
            }
            nStr = nStr + ".00";
            document.getElementById(id).value = nStr;
        }
    } else {
        var largo = my_str.indexOf(".");
        var centavos = my_str.substr(largo, my_str.length);
        if (my_str.indexOf(",") == -1) {
            var nStr = "";
            for (var i = 1; i <= largo; i++) {
                var desde = largo - i * 3;
                var hasta = largo - (3 * (i - 1));
                var temp = my_str.slice(desde, hasta);
                var separador = "";
                if (hasta > 3) {
                    if (miles == 1) {
                        miles = 0;
                        separador = ",";
                    } else {
                        miles = 1
                        separador = ",";
                    }
                    nStr = separador + temp + nStr;
                } else {
                    if (hasta > 0) {
                        temp = my_str.slice(0, hasta);
                        nStr = temp + nStr;
                    }
                }
            }
            nStr = nStr + centavos;
            document.getElementById(id).value = nStr;
        }
    }
}

/**
 * Caso Mantis 10432 : Verificar redondeo del valor asignado.
 * @Autor : Ellery David Robles Gómez.
 */
function formatoValor(my_str, id) {
    var miles = 1;
    if (my_str.indexOf(".") == -1) {
        if (my_str.indexOf(",") == -1) {
            var nStr = "";
            for (var i = 1; i <= my_str.length; i++) {
                var desde = my_str.length - i * 3;
                var hasta = my_str.length - (3 * (i - 1));
                var temp = my_str.slice(desde, hasta);
                var separador = "";
                if (hasta > 3) {
                    if (miles == 1) {
                        miles = 0;
                        separador = ",";
                    } else {
                        miles = 1
                        separador = ",";
                    }
                    nStr = separador + temp + nStr;
                } else {
                    if (hasta > 0) {
                        temp = my_str.slice(0, hasta);
                        nStr = temp + nStr;
                    }
                }
            }
            nStr = nStr + ".00";
            document.getElementById(id).value = nStr;
        }
    } else {
        var largo = my_str.indexOf(".");
        var centavos = my_str.substr(largo, my_str.length);
        if (my_str.indexOf(",") == -1) {
            var nStr = "";
            for (var i = 1; i <= largo; i++) {
                var desde = largo - i * 3;
                var hasta = largo - (3 * (i - 1));
                var temp = my_str.slice(desde, hasta);
                var separador = "";
                if (hasta > 3) {
                    if (miles == 1) {
                        miles = 0;
                        separador = ",";
                    } else {
                        miles = 1
                        separador = ",";
                    }
                    nStr = separador + temp + nStr;
                } else {
                    if (hasta > 0) {
                        temp = my_str.slice(0, hasta);
                        nStr = temp + nStr;
                    }
                }
            }
            nStr = nStr + centavos;
            document.getElementById(id).value = nStr;
        }
    }
}

function upperCaseBeforeSubmit() {
    for (j = 0; j < this.elements.length; j++) {
        f = this.elements[j];
        if (f.type == 'text' || f.type == 'textarea') {
            if (f.disabled)
                continue;
            if (f.readonly)
                continue;
            if (f.value) {
                var re = new RegExp('[a-z]');
                if (f.value.match(re)) {
                    f.value = f.value.toUpperCase();
                }
            }
        }
    }
    this._submit();
}
function upperCaseBeforeOnSubmit() {
    for (i = 0; i < document.forms.length; i++) {
        for (j = 0; j < document.forms[i].elements.length; j++) {
            f = document.forms[i].elements[j];
            if (f.type == 'text' || f.type == 'textarea') {
                if (f.disabled)
                    continue;
                if (f.readonly)
                    continue;
                if (f.value) {
                    var re = new RegExp('[a-z]');
                    if (f.value.match(re)) {
                        f.value = f.value.toUpperCase();
                    }
                }
            }
        }
    }
    if (this._onsubmit)
        this._onsubmit();
}

///////////////////////////////////////
//
//  Generic onload by Brothercake
//  http://www.brothercake.com/
//
///////////////////////////////////////



//onload function
function generic() {
    for (i = 0; i < document.forms.length; i++) {
        if (document.forms[i]._submit)
            continue;

        document.forms[i]._onsubmit = document.forms[i].onsubmit;
        document.forms[i].onsubmit = upperCaseBeforeOnSubmit;
        document.forms[i]._submit = document.forms[i].submit;
        document.forms[i].submit = upperCaseBeforeSubmit;
        for (j = 0; j < document.forms[i].elements.length; j++) {
            f = document.forms[i].elements[j];
            if (f.type == 'text' || f.type == 'textarea') {
                if (f.value) {
                    var re = new RegExp('[a-z]');
                    if (f.value.match(re)) {
                        f.value = f.value.toUpperCase();
                    }
                }
                //store it
                f._onblur = f.onblur;
                //add new onchange handler
                f.onblur = function () {
                    //call existing onchange function
                    if (this.value) {
                        var re = new RegExp('[a-z]');
                        if (this.value.match(re)) {
                            this.value = this.value.toUpperCase();
                        }
                    }
                    if (this._onblur)
                        this._onblur();
                };
            }
        }
    }
}
;

//setup onload function
if (typeof window.addEventListener != 'undefined')
{
    //.. gecko, safari, konqueror and standard
    window.addEventListener('load', generic, false);
} else if (typeof document.addEventListener != 'undefined')
{
    //.. opera 7
    document.addEventListener('load', generic, false);
} else if (typeof window.attachEvent != 'undefined')
{
    //.. win/ie
    window.attachEvent('onload', generic);
}

//** remove this condition to degrade older browsers
else
{
    //.. mac/ie5 and anything else that gets this far

    //if there's an existing onload function
    if (typeof window.onload == 'function')
    {
        //store it
        var existing = onload;

        //add new onload handler
        window.onload = function ()
        {
            //call existing onload function
            existing();

            //call generic onload function
            generic();
        };
    } else
    {
        //setup onload function
        window.onload = generic;
    }
}

function valideKey(evt) {
    var code = (evt.which) ? evt.which : evt.keyCode;

    if (code == 8) {
        return true;
    } else if (code >= 48 && code <= 57) {
        return true;
    } else {
        return false;
    }
}

function valideKeyDot(evt, txt) {
    var code = (evt.which) ? evt.which : evt.keyCode;
    var text = document.getElementById(txt).value;

    if (text.indexOf('.') > -1) {
        var res = text.split('.');
        var charsAfterDot = res[1];
        if (charsAfterDot.length > 3) {
            return false;
        }
    }

    if (code == 8) {
        return true;
    } else if (code >= 48 && code <= 57) {
        return true;
    } else if (code == 46) {
        if (text.indexOf('.') > -1) {
            return false;
        }
    } else {
        return false;
    }
}

function valideDot(text) {
    var area = document.getElementById(text).value;
    if (area.indexOf('.') > -1) {
        var res = area.split('.');
        var charsAfterDot = res[1];
        if (charsAfterDot.length > 3) {
            charsAfterDot = charsAfterDot.substr(0, 4);
            var resetDot = res[0] + '.' + charsAfterDot;
            if (area != resetDot) {
                document.getElementById(text).value = resetDot;
            }
        }
    }
}

function hectareasFormatter(hectareas, metros, centimetros) {
    var hectareasF = document.getElementById(hectareas).value;
    if (hectareasF.indexOf('.') > -1) {
        var unidadesMedidaH = hectareasF.split('.');
        hectareasF = unidadesMedidaH[0];
        var metrosS = '0.' + unidadesMedidaH[1];
        var metrosD = parseFloat(metrosS);
        metrosD *= 10000;
        document.getElementById(hectareas).value = hectareasF;
        document.getElementById(metros).value = Math.round(metrosD);
        document.getElementById(centimetros).value = '0';

    }
}

function hectareasFormatterByMetros(hectareas, metros, centimetros, valueM) {
    var hectareasF = valueM;
    var validarHectareas = document.getElementById(hectareas).value;
    if (hectareasF.indexOf('.') > -1) {
        var unidadesMedidaH = hectareasF.split('.');
        hectareasF = unidadesMedidaH[0];
        var metrosS = '0.' + unidadesMedidaH[1];
        var metrosD = parseFloat(metrosS);
        metrosD *= 10000;
        if (validarHectareas === '0' || validarHectareas === '') {
            document.getElementById(hectareas).value = hectareasF;
            document.getElementById(metros).value = Math.round(metrosD);
            document.getElementById(centimetros).value = '0';
        } else {
            alert('Inconsistencia en Areas: Verifique el valor de Hectareas');
            document.getElementById(hectareas).value = '0';
            document.getElementById(metros).value = '0';
            document.getElementById(centimetros).value = '0';
        }
    } else {
        if (validarHectareas === "0" || validarHectareas === "") {
            document.getElementById(hectareas).value = hectareasF;
            document.getElementById(metros).value = '0';
        } else {
            alert('Inconsistencia en Areas: Verifique el valor de Hectareas');
            document.getElementById(hectareas).value = '0';
            document.getElementById(metros).value = '0';
            document.getElementById(centimetros).value = '0';
        }
    }
}

function metrosFormatter(hectareas, metros, centimetros) {
    var metrosF = document.getElementById(metros).value;
    var validarHectareas = document.getElementById(hectareas).value;
    if (metrosF.indexOf('.') > -1) {
        var unidadesMedidaM = metrosF.split('.');
        if (unidadesMedidaM[0].length > 3) {
            var metrosS = unidadesMedidaM[0];
            var metrosD = parseFloat(metrosS);
            metrosD /= 10000;
            metrosS = metrosD.toString();
            hectareasFormatterByMetros(hectareas, metros, centimetros, metrosS);
        } else {
            document.getElementById(hectareas).value = '0';
            document.getElementById(metros).value = unidadesMedidaM[0];
        }
        var centimetrosF = '0.' + unidadesMedidaM[1];
        var centimetrosD = parseFloat(centimetrosF);
        centimetrosD *= 10000;
        document.getElementById(centimetros).value = Math.round(centimetrosD);
    } else {
        if (metrosF.length > 3) {
            var metrosS = metrosF;
            var metrosD = parseFloat(metrosS);
            metrosD /= 10000;
            metrosS = metrosD.toString();
            if (metrosS.indexOf('.') > -1) {
                var sinHectareas = metrosS.split('.');
                if (sinHectareas[0] != '0') {
                    hectareasFormatterByMetros(hectareas, metros, centimetros, metrosS);
                }
            } else {
                hectareasFormatterByMetros(hectareas, metros, centimetros, metrosS);
            }
        }
    }
}

function valideKeyBD(evt, txt) {
    var code = (evt.which) ? evt.which : evt.keyCode;
    var text = document.getElementById(txt).value;

    if (text.indexOf('.') > -1) {
        var res = text.split('.');
        var charsAfterDot = res[1];
        if (charsAfterDot.length > 3) {
            return false;
        }
    } else {
        if (text.length > 3) {
            if (code != 46) {
                return false;
            }
        }
    }

    if (code == 8) {
        return true;
    } else if (code >= 48 && code <= 57) {
        return true;
    } else if (code == 46) {
        if (text.includes('.')) {
            return false;
        }
    } else {
        return false;
    }
}

function valideBeforeDot(area, metros) {
    if (area.indexOf('.') > -1) {
        var res = area.split('.');
        var charsBeforeDot = res[0];
        if (charsBeforeDot.length > 3) {
            charsBeforeDot = charsBeforeDot.substr(0, 4);
            var resetDot = charsBeforeDot + '.' + res[1];
            if (area != resetDot) {
                document.getElementById(metros).value = resetDot;
            }
        }
    } else {
        if (area.length > 3) {
            area = area.substr(0, 4);
            document.getElementById(metros).value = area;
        }
    }
}

function onlyMetrosFormatter(metros, centimetros) {
    var metrosF = document.getElementById(metros).value;
    if (metrosF.indexOf('.') > -1) {
        var unidadesMedidaM = metrosF.split('.');
        if (unidadesMedidaM[0] > 3) {
            valideBeforeDot(metrosF, metros);
        }
        metrosF = document.getElementById(metros).value;
        var aux = metrosF.split('.');
        document.getElementById(metros).value = aux[0];
        var centimetrosF = '0.' + unidadesMedidaM[1];
        var centimetrosD = parseFloat(centimetrosF);
        centimetrosD *= 10000;
        document.getElementById(centimetros).value = Math.round(centimetrosD);
    } else {
        valideBeforeDot(metrosF, metros);
    }
}


function valideDate(evt, txt) {
    var code = (evt.which) ? evt.which : evt.keyCode;
    var date = document.getElementById(txt).value;
    var inx = date.indexOf('/');
    var counter = 0;
    var zero = (date.charAt(1) === '/' ? true : false);
    var monthBar = (zero ? 4 : 5);
    var yearEnd = (zero ? 9 : 10);
    var vDay;
    var vMonth = (date.indexOf('/') == -1 ? 2 : 3);
    var Month = (zero ? 2 : 3);
    var monthEnd = (zero ? 3 : 4);
    var lastDate = (zero ? 8 : 9);

    while (inx != -1) {
        counter++;
        inx = date.indexOf('/', inx + 1);
    }

    if (!zero && date.length === vMonth) {
        if (!(code >= 48 && code <= 49)) {
            return false;
        }
    }

    if (date.length === 1 && date.charAt(0) === '0') {
        if (code === 48) {
            return false;
        }
    }

    if (date.length === 2) {
        if (!(code >= 48 && code <= 49)) {
            return false;
        }
    }

    if (date.length === 2) {
        if (date.indexOf('/') == -1) {
            var day = date.charAt(0);
            if (day === '0') {
                date = date.substring(1, date.length);
            }
            date = date + '/';
            document.getElementById(txt).value = date;
        }
    } else if (date.length === monthBar) {
        date = date + '/';
        document.getElementById(txt).value = date;
    }

    if (!zero && date.length === 0) {
        if (!(code >= 48 && code <= 51)) {
            return false;
        }
    }

    if (!zero && date.charAt(0) === '3' && date.length === 1) {
        if (!(code >= 48 && code <= 49)) {
            return false;
        }
    }

    if (date.charAt(Month) === '1' && date.length === monthEnd) {
        if (!(code >= 48 && code <= 50)) {
            return false;
        }
    }

    if (date.charAt(Month) === '0' && date.length === monthEnd) {
        if (!(code >= 49 && code <= 57)) {
            return false;
        }
    }

    if (!zero && date.length === lastDate) {

    }

    if (date.length === yearEnd) {
        return false;
    } else {
        if (code == 8) {
            return true;
        } else if (code >= 48 && code <= 57) {
            return true;
        } else {
            return false;
        }
    }
}

function fixDate(txt) {
    var date = document.getElementById(txt).value;
    var zero = (date.charAt(1) === '/' ? true : false);
    var leap = false;

    if (date.length === 9 || date.length === 10) {
        if (!zero) {
            var splittedDate = date.split("/");
            var day = splittedDate[0];
            var month = splittedDate[1];
            var year = splittedDate[2];

            if (day === '0') {
                alert('La fecha no es valida');
                document.getElementById(txt).value = '';
            }
            if (month > 12) {
                alert('La fecha no es valida');
                document.getElementById(txt).value = '';
            }
            if ((((year % 100) != 0) && ((year % 4) == 0)) || ((year % 400) == 0)) {
                leap = true;
            }

            if (month == '04' || month == '06' || month == '09' || month == '11') {
                if (day == '31') {
                    day = '30';
                }
            } else if (month == '02') {
                if (day > '28') {
                    day = (leap ? 29 : 28);
                }
            }
            date = document.getElementById(txt).value;
            if (date.length > 0) {
                document.getElementById(txt).value = day + '/' + month + '/' + year;
            }
        }
    } else {
        alert('La fecha ingresada no está en el formato apropiado: dd/mm/aaaa');
        document.getElementById(txt).value = '';
    }
}