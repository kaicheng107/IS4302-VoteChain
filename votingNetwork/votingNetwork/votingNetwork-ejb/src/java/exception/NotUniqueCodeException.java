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
public class NotUniqueCodeException extends Exception {

    /**
     * Creates a new instance of <code>NotUniqueCodeException</code> without
     * detail message.
     */
    public NotUniqueCodeException() {
    }

    /**
     * Constructs an instance of <code>NotUniqueCodeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotUniqueCodeException(String msg) {
        super(msg);
    }
}
