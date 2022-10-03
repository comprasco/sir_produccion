/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.constantes;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiService;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Cristian Garcia PS
 */
public final class CError {
    
    public static String errorAvanzarNotificador(Turno turno){
        
        String grandError = "";
        
       
        if(turno != null && turno.getSolicitud().getSolicitudFolios() != null){
            List solFolios = turno.getSolicitud().getSolicitudFolios();
            if(solFolios != null && !solFolios.isEmpty()){
                Iterator itSol = solFolios.iterator();
                while(itSol.hasNext()){
                    SolicitudFolio solF = (SolicitudFolio) itSol.next();
                    String error = "La matricula " + solF.getFolio().getIdMatricula();
                    error += " se encuentra bloqueada por turnos en fase de Notificación Nota Devolutiva: " ;
                    try{
                        ForsetiService fs = ForsetiService.getInstance();
                        List wFolios = fs.getTurnsShareFolioNotaDev(solF.getFolio().getIdMatricula());
                        if(wFolios != null && !wFolios.isEmpty()){
                            
                            Iterator itTurn = wFolios.iterator();
                            int i = 0;
                            while(itTurn.hasNext()){
                                String turnoWF = (String) itTurn.next();
                                if(i <= 0){
                                    error += " " + turnoWF;
                                    i++;
                                } else{
                                    error += ", " + turnoWF;
                                }  
                            }
                            grandError += error + "</br>";
                        }
                    } catch(ForsetiException fe){
                        System.out.println("ERROR: " + fe);
                    }
                }
            }
        }  
               
        return grandError;
    }
    
   private static String getFirstTurno(List turnos){
       String firstTurn = "";
       Turno first = null;
       try{
           HermodService hs = HermodService.getInstance();
           Iterator itTurn = turnos.iterator();
           while(itTurn.hasNext()){
               String turno = (String) itTurn.next();
               Turno turn = hs.getTurnobyWF(turno);
               if(first != null){
                   if(first.getFechaInicio().after(turn.getFechaInicio())){
                       first = turn;
                   }
               } else{
                   first = turn;
               }
           }
       } catch (HermodException he){
           System.out.println("ERROR" + he);
       }
       
       firstTurn = first!=null?first.getIdWorkflow():null;
       return firstTurn;
   }
    
    public static String validarPrincipioPrioridadNotNotaDev(Turno turno){
        
        String grandError = ""; 
        boolean isOk = false;
        if(turno != null && turno.getSolicitud().getSolicitudFolios() != null){
            List solFolios = turno.getSolicitud().getSolicitudFolios();
            if(solFolios != null && !solFolios.isEmpty()){
                Iterator itSol = solFolios.iterator();
                grandError += "Existen turnos activos anteriores que comparten folios y no han sido notificados: ";
                while(itSol.hasNext()){
                    SolicitudFolio solF = (SolicitudFolio) itSol.next();
                    try{
                        ForsetiService fs = ForsetiService.getInstance();
                        List wFolios = fs.validarPrincipioPrioridadNotNotaDev(solF.getFolio().getIdMatricula());
                        String first = getFirstTurno(wFolios);
                        if(wFolios != null && wFolios.size() > 1){
                            Iterator itTurn = wFolios.iterator();
                            int i = 0;
                            while(itTurn.hasNext()){
                                String turnoWF = (String) itTurn.next();
                                if(turno.getIdWorkflow().equals(first)){
                                    isOk = true;
                                    break;
                                } else{
                                   if(!turno.getIdWorkflow().equals(turnoWF)){
                                    if (i <= 0){
                                        grandError += turnoWF;
                                        isOk = false;
                                        i++;
                                    } else{
                                        grandError += ", " + turnoWF;
                                        isOk = false;
                                    }
                                }}
                            }
                        } else{
                            isOk = true;
                        }
                    } catch(ForsetiException fe){
                        System.out.println("ERROR: " + fe);
                    } 
                }
            } else{
                isOk = true;
            }
        }  
         
        if(isOk){
            return null;
        }
        return grandError;
    }
    
    public static String validarPrincipioPrioridadNotNotaNot(Turno turno){
        
         String grandError = ""; 
        boolean isOk = false;
        if(turno != null && turno.getSolicitud().getSolicitudFolios() != null){
            List solFolios = turno.getSolicitud().getSolicitudFolios();
            if(solFolios != null && !solFolios.isEmpty()){
                Iterator itSol = solFolios.iterator();
                grandError += "Existen turnos activos anteriores que comparten folios y no han sido notificados: ";
                while(itSol.hasNext()){
                    SolicitudFolio solF = (SolicitudFolio) itSol.next();
                    try{
                        ForsetiService fs = ForsetiService.getInstance();
                        List wFolios = fs.validarPrincipioPrioridadNotNotaNot(solF.getFolio().getIdMatricula());
                        String first = getFirstTurno(wFolios);
                        if(wFolios != null && wFolios.size() > 1){
                            Iterator itTurn = wFolios.iterator();
                            int i = 0;
                            while(itTurn.hasNext()){
                                String turnoWF = (String) itTurn.next();
                                if(turno.getIdWorkflow().equals(first)){
                                    isOk = true;
                                    break;
                                } else{
                                   if(!turno.getIdWorkflow().equals(turnoWF)){
                                    if (i <= 0){
                                        grandError += turnoWF;
                                        isOk = false;
                                        i++;
                                    } else{
                                        grandError += ", " + turnoWF;
                                        isOk = false;
                                    }
                                }}
                            }
                        } else{
                            isOk = true;
                        }
                    } catch(ForsetiException fe){
                        System.out.println("ERROR: " + fe);
                    } 
                }
            } else{
                isOk = true;
            }
        }  
         
        if(isOk){
            return null;
        }
        return grandError;
    }
    
    public static String validarPrincipioPrioridadRecNot(Turno turno){
        
         String grandError = ""; 
        boolean isOk = false;
        if(turno != null && turno.getSolicitud().getSolicitudFolios() != null){
            List solFolios = turno.getSolicitud().getSolicitudFolios();
            if(solFolios != null && !solFolios.isEmpty()){
                Iterator itSol = solFolios.iterator();
                grandError += "Existen turnos activos anteriores que comparten folios y no han sido notificados: ";
                while(itSol.hasNext()){
                    SolicitudFolio solF = (SolicitudFolio) itSol.next();
                    try{
                        ForsetiService fs = ForsetiService.getInstance();
                        List wFolios = fs.validarPrincipioPrioridadRecNot(solF.getFolio().getIdMatricula());
                        String first = getFirstTurno(wFolios);
                        if(wFolios != null && wFolios.size() > 1){
                            Iterator itTurn = wFolios.iterator();
                            int i = 0;
                            while(itTurn.hasNext()){
                                String turnoWF = (String) itTurn.next();
                                if(turno.getIdWorkflow().equals(first)){
                                    isOk = true;
                                    break;
                                } else{
                                   if(!turno.getIdWorkflow().equals(turnoWF)){
                                    if (i <= 0){
                                        grandError += turnoWF;
                                        isOk = false;
                                        i++;
                                    } else{
                                        grandError += ", " + turnoWF;
                                        isOk = false;
                                    }
                                }}
                                
                            }
                        } else{
                            isOk = true;
                        }
                    } catch(ForsetiException fe){
                        System.out.println("ERROR: " + fe);
                    } 
                }
            } else{
                isOk = true;
            }
        }  
         
        if(isOk){
            return null;
        }
        return grandError;
    }
    
}
