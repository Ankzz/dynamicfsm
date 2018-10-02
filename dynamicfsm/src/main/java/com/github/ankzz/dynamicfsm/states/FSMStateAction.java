/**
 *                    GNU LESSER GENERAL PUBLIC LICENSE
 *                          Version 3, 29 June 2007
 * Copyright (C) 2018 Free Software Foundation, Inc. <http://fsf.org>
 * 
 * This file is part of library dynamicfsm.
 * 
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU LESSER GENERAL PUBLIC LICENSE as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This library can be redistributed
 * or used in case this license is copied as it is.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Author : Ankit
 * Report bugs to : ankzzdev (at) gmail (dot) com
**/
package com.github.ankzz.dynamicfsm.states;

/**
 *
 * @version 0.01
 * @author Ankit
 */
public interface FSMStateAction {
    /**
     * 
     * @param state State with which Action needs to be associated
     * @param arg Action to be associated with the state
     */
    public void stateTransition(String state, Object arg);
}
