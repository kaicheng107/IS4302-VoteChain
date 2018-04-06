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
public class VoterNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>VoterNotFoundException</code> without
     * detail message.
     */
    public VoterNotFoundException() {
    }

    /**
     * Constructs an instance of <code>VoterNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VoterNotFoundException(String msg) {
        super(msg);
    }
}
