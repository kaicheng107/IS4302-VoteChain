/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author KaiCheng
 */
public class EDSNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>EDSNotFoundException</code> without
     * detail message.
     */
    public EDSNotFoundException() {
    }

    /**
     * Constructs an instance of <code>EDSNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EDSNotFoundException(String msg) {
        super(msg);
    }
}
