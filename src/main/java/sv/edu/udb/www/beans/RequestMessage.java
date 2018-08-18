/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.beans;

/**
 *
 * @author Frank Esquivel
 */
public class RequestMessage {
    private final int code;
    private final String message;
    private final String status;

    public RequestMessage(int code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
