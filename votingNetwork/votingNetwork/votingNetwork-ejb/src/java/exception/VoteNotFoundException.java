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
public class VoteNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>VoteNotFoundException</code> without
     * detail message.
     */
    public VoteNotFoundException() {
    }

    /**
     * Constructs an instance of <code>VoteNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VoteNotFoundException(String msg) {
        super(msg);
    }
}
