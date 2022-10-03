//Global variables
var winCal;
var dtToday=new Date();
var Cal;
var docCal;
var MonthName=["01", "02", "03", "04", "05", "06","07", 
	"08", "09", "10", "11", "12"];
var WeekDayName=["Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"];	
var exDateTime;

//Configurable parameters

var cnTop="200";//top coordinate of calendar window.
var cnLeft="500";//left coordinate of calendar window
var WindowTitle ="Calendario ::.... SNR";//title.
var WeekChar=2;//number of character for week day. if 2 then Mo,Tu,We. if 3 then Mon,Tue,Wed.
var CellWidth=20;//Width of day cell.
var DateSeparator="/";//Date Separator, you can change it to "/" if you want.
var ShowLongMonth=true;//Show long month name in Calendar header. example: "January".
var ShowMonthYear=true;//Show Month and Year in Calendar header.
var MonthYearColor="";//Font Color of Month and Year in Calendar header.
var WeekHeadColor="";//Background Color in Week header.
var SundayColor="campostip04";//Background color of Sunday.
var SaturdayColor="campostip04";//Background color of Saturday.
var WeekDayColor="campostip03";//Background color of weekdays.
var FontColor="campostip03";//color of font in Calendar day cell.
var TodayColor="camposformtext";//Background color of today.
var SelDateColor="campostip03";//Backgrond color of selected date in textbox.
var YrSelColor="";//color of font of Year selector.
var ThemeBg="";//Background image of Calendar window.
var pathreal;
function Valores(text) {
pathreal = text
}

function NewCal(pCtrl,pFormat,pShowTime,pTimeMode)
{
	Cal=new Calendar(dtToday);
	if ((pShowTime!=null) && (pShowTime))
	{
		Cal.ShowTime=true;
		if ((pTimeMode!=null) &&((pTimeMode=='12')||(pTimeMode=='24')))
		{
			TimeMode=pTimeMode;
		}		
	}	
	if (pCtrl!=null)
		Cal.Ctrl=pCtrl;
	if (pFormat!=null)
		Cal.Format=pFormat.toUpperCase();
	
	exDateTime=document.getElementById(pCtrl).value;
	if (exDateTime!="")//Parse Date String
	{
		
		var Sp1;//Index of Date Separator 1
		var Sp2;//Index of Date Separator 2 
		var tSp1;//Index of Time Separator 1
		var tSp1;//Index of Time Separator 2
		var strMonth;
		var strDate;
		var strYear;
		var intMonth;
		var YearPattern;
		var strHour;
		var strMinute;
		var strSecond;
		//parse month
		Sp1=exDateTime.indexOf(DateSeparator,0)
		Sp2=exDateTime.indexOf(DateSeparator,(parseInt(Sp1)+1));
		
		if ((Cal.Format.toUpperCase()=="DDMMYYYY") || (Cal.Format.toUpperCase()=="DDMMMYYYY"))
		{
			strMonth=exDateTime.substring(Sp1+1,Sp2);
			strDate=exDateTime.substring(0,Sp1);
		}
		else if ((Cal.Format.toUpperCase()=="MMDDYYYY") || (Cal.Format.toUpperCase()=="MMMDDYYYY"))
		{
			strMonth=exDateTime.substring(0,Sp1);
			strDate=exDateTime.substring(Sp1+1,Sp2);
		}
		if (isNaN(strMonth))
			intMonth=Cal.GetMonthIndex(strMonth);
		else
			intMonth=parseInt(strMonth,10)-1;	
		if ((parseInt(intMonth,10)>=0) && (parseInt(intMonth,10)<12))
			Cal.Month=intMonth;
		//end parse month
		//parse Date
		if ((parseInt(strDate,10)<=Cal.GetMonDays()) && (parseInt(strDate,10)>=1))
			Cal.Date=strDate;
		//end parse Date
		//parse year
		strYear=exDateTime.substring(Sp2+1,Sp2+5);
		YearPattern=/^\d{4}$/;
		if (YearPattern.test(strYear))
			Cal.Year=parseInt(strYear,10);
		//end parse year
	}
	winCal=window.open("","calendario","toolbar=0,status=0,menubar=0,fullscreen=no,width=500,height=310,resizable=0,top="+cnTop+",left="+cnLeft);
	docCal=winCal.document;
	RenderCal();
}

function RenderCal()
{
	var vCalHeader;
	var vCalData;
	var vCalTime;
	var i;
	var j;
	var SelectStr;
	var vDayCount=0;
	var vFirstDay;

	docCal.open();
	docCal.writeln("<%@ page contentType=\"text/html; charset=iso-8859-1\" language=\"java\" import=\"java.sql.*\" errorPage=\"\" %>");
	docCal.writeln("<html>");
	docCal.writeln("<head><title>"+WindowTitle+"</title>");
	docCal.writeln("<link rev='made' href='mailto:contact@rainforestnet.com'>");
	docCal.writeln("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
	docCal.writeln("<meta name='generator' content='NoteTab Light 4.9'>");
	docCal.writeln("<meta name='author' content='TengYong Ng'>");
	docCal.writeln("<meta name='description' content=''>");
	docCal.writeln("<meta name='keywords' content=''>");
	docCal.writeln("<meta name='Author' content='Inform&aacute;tica Siglo 21 - Equant'>");
	docCal.writeln("<meta name='Keywords' content='inicio, sesion, login, password, clave, usuario, user'>");
	docCal.writeln("<link href='"+pathreal+"/jsp/plantillas/style.css' rel='stylesheet' type='text/css'>");
	docCal.writeln("<script>var winMain=window.opener;</script>");
	docCal.writeln("<body bgcolor='#FFFFFF' leftmargin='0' topmargin='0' marginwidth='0' marginheight='0'>");
	docCal.writeln("<table width='100%' border='0' cellpadding='0' cellspacing='0'>");
	docCal.writeln("  <tr> ");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("  </tr>");
	docCal.writeln("  <tr> ");
	docCal.writeln("    <td width='12'>&nbsp;</td>");
	docCal.writeln("    <td width='12'><img name='tabla_gral_r1_c1' src='"+pathreal+"/jsp/images/tabla_gral_r1_c1.gif' width='12' height='23' border='0' alt=''></td>");
	docCal.writeln("    <td background='"+pathreal+"/jsp/images/tabla_gral_bgn002.gif'> <table border='0' cellpadding='0' cellspacing='0'>");
	docCal.writeln("        <tr> ");
	docCal.writeln("          <td background='"+pathreal+"/jsp/images/tabla_gral_bgn001.gif' class='titulotbgral'>Calendario</td>");
	docCal.writeln("          <td width='28'><img name='tabla_gral_r1_c3' src='"+pathreal+"/jsp/images/tabla_gral_r1_c3.gif' width='28' height='23' border='0' alt=''></td>");
	docCal.writeln("        </tr>");
	docCal.writeln("      </table></td>");
	docCal.writeln("    <td width='12'><img name='tabla_gral_r1_c5' src='"+pathreal+"/jsp/images/tabla_gral_r1_c5.gif' width='12' height='23' border='0' alt=''></td>");
	docCal.writeln("    <td width='12'>&nbsp;</td>");
	docCal.writeln("  </tr>");
	docCal.writeln("  <tr> ");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("    <td background='"+pathreal+"/jsp/images/tabla_gral_bgn003.gif'>&nbsp;</td>");
	docCal.writeln("    <td class='tdtablaanexa02'><form name='Calendar'><table border='0' cellpadding='0' cellspacing='0' width='100%'>");
	docCal.writeln("        <!-- fwtable fwsrc='SIR_central.png' fwbase='tabla_central.gif' fwstyle='Dreamweaver' fwdocid = '742308039' fwnested='1' -->");
	docCal.writeln("        <tr> ");
	docCal.writeln("          <td><img src='"+pathreal+"/jsp/images/spacer.gif' width='7' height='10'></td>");
	docCal.writeln("          <td width='847' background='"+pathreal+"/jsp/images/tabla_central_bgn003.gif'><img src='"+pathreal+"/jsp/images/spacer.gif' width='10' height='10'></td>");
	docCal.writeln("          <td><img src='"+pathreal+"/jsp/images/spacer.gif' width='10' height='10'></td>");
	docCal.writeln("        </tr>");
	docCal.writeln("        <tr> ");
	docCal.writeln("          <td><img name='tabla_central_r1_c1' src='"+pathreal+"/jsp/images/tabla_central_r1_c1.gif' width='7' height='29' border='0' alt=''></td>");
	docCal.writeln("          <td background='"+pathreal+"/jsp/images/tabla_central_bgn003.gif'> <table border='0' cellpadding='0' cellspacing='0'>");
	docCal.writeln("              <tr> ");
	docCal.writeln("                <td background='"+pathreal+"/jsp/images/tabla_central_bgn001.gif' class='titulotbcentral'>Calendario</td>");
	docCal.writeln("                <td width='9'><img name='tabla_central_r1_c3' src='"+pathreal+"/jsp/images/tabla_central_r1_c3.gif' width='9' height='29' border='0' alt=''></td>");
	docCal.writeln("                <td width='20' align='center' valign='top' background='"+pathreal+"/jsp/images/tabla_central_bgn002.gif'> ");
	docCal.writeln("                  <table width='100%' border='0' cellspacing='0' cellpadding='0'>");
	docCal.writeln("                    <tr> ");
	docCal.writeln("                      <td><img src='"+pathreal+"/jsp/images/ico_calendario.gif' width='16' height='21'></td>");
	docCal.writeln("                    </tr>");
	docCal.writeln("                  </table></td>");
	docCal.writeln("                <td width='12'><img name='tabla_central_r1_c5' src='"+pathreal+"/jsp/images/tabla_central_r1_c5.gif' width='12' height='29' border='0' alt=''></td>");
	docCal.writeln("              </tr>");
	docCal.writeln("            </table></td>");
	docCal.writeln("          <td><img name='tabla_central_pint_r1_c7' src='"+pathreal+"/jsp/images/tabla_central_pint_r1_c7.gif' width='11' height='29' border='0' alt=''></td>");
	docCal.writeln("        </tr>");
	docCal.writeln("        <tr> ");
	docCal.writeln("          <td width='7' background='"+pathreal+"/jsp/images/tabla_central_bgn009.gif'>&nbsp;</td>");
	docCal.writeln("          <td valign='top' bgcolor='#79849B' class='tdtablacentral'>");

	vCalHeader="            <table width='100%' border='0' cellspacing='0' cellpadding='0'>\n"
 	vCalHeader+="                <tr>\n "
	//Selecion Mes
	vCalHeader+="                  <td><select name='MonthSelector' class='camposformtext' onChange='javascript:winMain.Cal.SwitchMth(this.selectedIndex);winMain.RenderCal();'>\n"
	for (i=0;i<12;i++)
	{
		if (i==Cal.Month)
			SelectStr="Selected";
		else
			SelectStr="";	
		vCalHeader+="                      <option "+SelectStr+" value >"+MonthName[i]+"\n";
	}
	vCalHeader+="                      </select></td>\n"
	vCalHeader+="                  <td align='right'> <table border='0' cellspacing='2' cellpadding='0'>\n"	
	vCalHeader+="                      <tr>\n"
	vCalHeader+="                        <td width='16'><a href='javascript:winMain.Cal.DecYear();winMain.RenderCal()'><img src='"+pathreal+"/jsp/images/btn_atras.gif' width='16' height='16' border='0'></a></td>\n"
	//Año
	vCalHeader+="                        <td align='center' class='titulotbcentral'>"+Cal.Year+"</td>\n"
	vCalHeader+="                        <td width='16'><a href='javascript:winMain.Cal.IncYear();winMain.RenderCal()'><img src='"+pathreal+"/jsp/images/btn_adelante.gif' width='16' height='16' border='0'></a></td>\n"
	vCalHeader+="                      </tr>\n"
	vCalHeader+="                    </table></td>\n"
	vCalHeader+="                </tr>\n"
	vCalHeader+="              </table>\n"
	//Calendar header shows Month and Year
	if (ShowMonthYear)
		vCalHeader+="              <table width='100%' border='0' cellspacing='0' cellpadding='0'>\n"
		vCalHeader+="                <tr> \n"
		vCalHeader+="                  <td class='titulotbcentral'>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</td>\n"
		vCalHeader+="                </tr>\n"
		vCalHeader+="              </table>\n"
	//Dias
	vCalHeader+="              <hr class='linehorizontal'>\n"
	vCalHeader+="              <table width='100%' border='0' cellpadding='0' cellspacing='0' class='camposform'>\n"
	vCalHeader+="<!--aqui-->                <tr align='center' class='campostitle'> "
	for (i=0;i<7;i++)
	{
		vCalHeader+="                  <td class='campostip02'>"+WeekDayName[i].substr(0,WeekChar)+"\n</td>\n";
	}
	vCalHeader+="                  	             </tr>\n"
	docCal.write(vCalHeader);
	
	//Detalle Calendario
	CalDate=new Date(Cal.Year,Cal.Month);
	CalDate.setDate(0);
	vFirstDay=CalDate.getDay();
	vCalData="                <tr>";
	for (i=0;i<vFirstDay;i++)
	{
		vCalData=vCalData+GenCell();
		vDayCount=vDayCount+1;
	}
	for (j=1;j<=Cal.GetMonDays();j++)
	{
		var strCell;
		vDayCount=vDayCount+1;
		if ((j==dtToday.getDate())&&(Cal.Month==dtToday.getMonth())&&(Cal.Year==dtToday.getFullYear()))
			strCell="                  "+GenCell(j,true,TodayColor);//Highlight today's date
		else
		{
			if (j==Cal.Date)
			{
				strCell="                  "+GenCell(j,true,SelDateColor);
			}
			else
			{	 
				if (vDayCount%7==6)
					strCell="                  "+GenCell(j,false,SaturdayColor);
				else if ((vDayCount+6)%7==6)
					strCell="                  "+GenCell(j,false,SundayColor);
				else
					strCell="                  "+GenCell(j,null,WeekDayColor);
			}		
		}						
		vCalData=vCalData+strCell;
		if((vDayCount%7==0)&&(j<Cal.GetMonDays()))
		{
			vCalData=vCalData+"                  	             </tr>\n";
		}
	}
	docCal.writeln(vCalData);
	docCal.writeln("              </table>");
	docCal.writeln("              <br>");
	docCal.writeln("            </td>");
	docCal.writeln("          <td width='12' background='"+pathreal+"/jsp/images/tabla_central_bgn008.gif'>&nbsp;</td>");
	docCal.writeln("        </tr>");
	docCal.writeln("        <tr> ");
	docCal.writeln("          <td><img name='tabla_central_r3_c1' src='"+pathreal+"/jsp/images/tabla_central_r3_c1.gif' width='7' height='6' border='0' alt=''></td>");
	docCal.writeln("          <td background='"+pathreal+"/jsp/images/tabla_central_bgn006.gif'><img src='"+pathreal+"/jsp/images/spacer.gif' width='15' height='6'></td>");
	docCal.writeln("          <td><img name='tabla_central_pint_r3_c7' src='"+pathreal+"/jsp/images/tabla_central_pint_r3_c7.gif' width='11' height='6' border='0' alt=''></td>");
	docCal.writeln("        </tr>");
	docCal.writeln("      </table></form>      ");
	docCal.writeln("    </td>");
	docCal.writeln("    <td background='"+pathreal+"/jsp/images/tabla_gral_bgn004.gif'>&nbsp;</td>");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("  </tr>");
	docCal.writeln("  <tr> ");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("    <td><img name='tabla_gral_r3_c1' src='"+pathreal+"/jsp/images/tabla_gral_r3_c1.gif' width='12' height='20' border='0' alt=''></td>");
	docCal.writeln("    <td background='"+pathreal+"/jsp/images/tabla_gral_bgn005.gif'>&nbsp;</td>");
	docCal.writeln("    <td><img name='tabla_gral_r3_c5' src='"+pathreal+"/jsp/images/tabla_gral_r3_c5.gif' width='12' height='20' border='0' alt=''></td>");
	docCal.writeln("    <td>&nbsp;</td>");
	docCal.writeln("  </tr>");
	docCal.writeln("</table>");
	docCal.writeln("</body>");
	docCal.writeln("</html>");
	docCal.close();
}

function GenCell(pValue,pHighLight,pColor)//Generate table cell with value
{
	var PValue;
	var PCellStr;
	var vColor;
	var vHLstr1;//HighLight string
	var vHlstr2;
	var vTimeStr;
	
	if (pValue==null)
		PValue="";
	else
		PValue=pValue;
	
	if (pColor!=null)
		vColor="Class=\""+pColor+"\"";
	else
		vColor="";	
	if ((pHighLight!=null)&&(pHighLight))
		{vHLstr1="align=\"center\""+vColor;vHLstr2="";}
	else
		{vHLstr1="align=\"center\""+vColor;vHLstr2="";}	
	
	if (Cal.ShowTime)
	{
		vTimeStr="winMain.document.getElementById('"+Cal.Ctrl+"').value+=''";
		if (TimeMode==12)
			vTimeStr+="";
	}	
	else
		vTimeStr="winMain.document.getElementById('"+Cal.Ctrl+"').value+=''";;		
	
	PCellStr="	                  <td "+vHLstr1+"><a href=\"javascript:winMain.document.getElementById('"+Cal.Ctrl+"').value='"+Cal.FormatDate(PValue)+"';"+vTimeStr+";window.close();\" class=\"links\">"+PValue+"</a>"+vHLstr2+"</td>\n";
	return PCellStr;
}

function Calendar(pDate,pCtrl)
{
	//Properties
	this.Date=pDate.getDate();//selected date
	this.Month=pDate.getMonth();//selected month number
	this.Year=pDate.getFullYear();//selected year in 4 digits
	this.Hours=pDate.getHours();	
	
	if (pDate.getMinutes()<10)
		this.Minutes="0"+pDate.getMinutes();
	else
		this.Minutes=pDate.getMinutes();
	
	if (pDate.getSeconds()<10)
		this.Seconds="0"+pDate.getSeconds();
	else		
		this.Seconds=pDate.getSeconds();
		
	this.MyWindow=winCal;
	this.Ctrl=pCtrl;
	this.Format="ddMMyyyy";
	this.Separator=DateSeparator;
	this.ShowTime=false;
	if (pDate.getHours()<12)
		this.AMorPM="AM";
	else
		this.AMorPM="PM";	
}

function GetMonthIndex(shortMonthName)
{
	for (i=0;i<12;i++)
	{
		if (MonthName[i].substring(0,3).toUpperCase()==shortMonthName.toUpperCase())
		{	return i;}
	}
}
Calendar.prototype.GetMonthIndex=GetMonthIndex;

function IncYear()
{	Cal.Year++;}
Calendar.prototype.IncYear=IncYear;

function DecYear()
{	Cal.Year--;}
Calendar.prototype.DecYear=DecYear;
	
function SwitchMth(intMth)
{	Cal.Month=intMth;}
Calendar.prototype.SwitchMth=SwitchMth;

function SetHour(intHour)
{	
	var MaxHour;
	var MinHour;
	if (TimeMode==24)
	{	MaxHour=23;MinHour=0}
	else if (TimeMode==12)
	{	MaxHour=12;MinHour=1}
	else
		alert("TimeMode can only be 12 or 24");		
	var HourExp=new RegExp("^\\d\\d$");
	if (HourExp.test(intHour) && (parseInt(intHour,10)<=MaxHour) && (parseInt(intHour,10)>=MinHour))
	{	
		if ((TimeMode==12) && (Cal.AMorPM=="PM"))
		{
			if (parseInt(intHour,10)==12)
				Cal.Hours=12;
			else	
				Cal.Hours=parseInt(intHour,10)+12;
		}	
		else if ((TimeMode==12) && (Cal.AMorPM=="AM"))
		{
			if (intHour==12)
				intHour-=12;
			Cal.Hours=parseInt(intHour,10);
		}
		else if (TimeMode==24)
			Cal.Hours=parseInt(intHour,10);	
	}
}
Calendar.prototype.SetHour=SetHour;

function SetMinute(intMin)
{
	var MinExp=new RegExp("^\\d\\d$");
	if (MinExp.test(intMin) && (intMin<60))
		Cal.Minutes=intMin;
}
Calendar.prototype.SetMinute=SetMinute;

function SetSecond(intSec)
{	
	var SecExp=new RegExp("^\\d\\d$");
	if (SecExp.test(intSec) && (intSec<60))
		Cal.Seconds=intSec;
}
Calendar.prototype.SetSecond=SetSecond;

function SetAmPm(pvalue)
{
	this.AMorPM=pvalue;
	if (pvalue=="PM")
	{
		this.Hours=(parseInt(this.Hours,10))+12;
		if (this.Hours==24)
			this.Hours=12;
	}	
	else if (pvalue=="AM")
		this.Hours-=12;	
}
Calendar.prototype.SetAmPm=SetAmPm;

function getShowHour()
{
	var finalHour;
    if (TimeMode==12)
    {
    	if (parseInt(this.Hours,10)==0)
		{
			this.AMorPM="AM";
			finalHour=parseInt(this.Hours,10)+12;	
		}
		else if (parseInt(this.Hours,10)==12)
		{
			this.AMorPM="PM";
			finalHour=12;
		}		
		else if (this.Hours>12)
		{
			this.AMorPM="PM";
			if ((this.Hours-12)<10)
				finalHour="0"+((parseInt(this.Hours,10))-12);
			else
				finalHour=parseInt(this.Hours,10)-12;	
		}
		else
		{
			this.AMorPM="AM";
			if (this.Hours<10)
				finalHour="0"+parseInt(this.Hours,10);
			else
				finalHour=this.Hours;	
		}
	}
	else if (TimeMode==24)
	{
		if (this.Hours<10)
			finalHour="0"+parseInt(this.Hours,10);
		else	
			finalHour=this.Hours;
	}	
	return finalHour;	
}				
Calendar.prototype.getShowHour=getShowHour;		

function GetMonthName(IsLong)
{
	var Month=MonthName[this.Month];
	if (IsLong)
		return Month;
	else
		return Month.substr(0,10);
}
Calendar.prototype.GetMonthName=GetMonthName;

function GetMonDays()//Get number of days in a month
{
	var DaysInMonth=[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if (this.IsLeapYear())
	{
		DaysInMonth[1]=29;
	}	
	return DaysInMonth[this.Month];	
}
Calendar.prototype.GetMonDays=GetMonDays;

function IsLeapYear()
{
	if ((this.Year%4)==0)
	{
		if ((this.Year%100==0) && (this.Year%400)!=0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	else
	{
		return false;
	}
}
Calendar.prototype.IsLeapYear=IsLeapYear;

function FormatDate(pDate)
{
	if (this.Format.toUpperCase()=="DDMMYYYY")
		return (pDate+DateSeparator+(this.Month+1)+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="DDMMMYYYY")
		return (pDate+DateSeparator+this.GetMonthName(false)+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMDDYYYY")
		return ((this.Month+1)+DateSeparator+pDate+DateSeparator+this.Year);
	else if (this.Format.toUpperCase()=="MMMDDYYYY")
		return (this.GetMonthName(false)+DateSeparator+pDate+DateSeparator+this.Year);			
}
Calendar.prototype.FormatDate=FormatDate;	