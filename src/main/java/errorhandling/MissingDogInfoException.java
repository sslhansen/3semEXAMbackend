/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author Tas
 */
public class MissingDogInfoException extends Exception {

    public MissingDogInfoException() {
        super("Missing dog information!");
    }

}
