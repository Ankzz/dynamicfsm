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
package com.github.ankzz.dynamicfsm.fsm;

import com.github.ankzz.dynamicfsm.action.FSMAction;
import com.github.ankzz.dynamicfsm.states.FSMState;
import com.github.ankzz.dynamicfsm.states.FSMStateAction;
import com.github.ankzz.dynamicfsm.states.FSMStates;
import com.github.ankzz.dynamicfsm.states.FSMTransitionInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Class to allow creation of the FSM
 * 
 * This class allows developer to either specify the XML Configuration File or
 * can pass the input-stream of the XML Configuration File.
 * Configuration file's format is as follows:
 * 
 * <p>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 *
 * &lt;!--
 *  &nbsp;Document   : config.xml
 *  &nbsp;Created on : 22 March, 2013, 9:05 AM
 *  &nbsp;Author     : ANKIT
 *  &nbsp;Description:
 *      &nbsp;&nbsp;&nbsp;&nbsp;File specifies states and transition of an FSM.
 *      &nbsp;&nbsp;&nbsp;&nbsp;This is an example file.
 * --&gt;
 * 
 * &lt;FSM&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;STATE id="START" type="ID"&gt;
 *		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *              &lt;MESSAGE id="MOVE" action="move" nextState="START"/&gt;
 *		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *              &lt;MESSAGE id="MOVELEFT" action="moveLeft" nextState="INTERMEDIATE"/&gt;
 *		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *              &lt;MESSAGE id="MOVERIGHT" action="moveRight" nextState="STOP"/&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;/STATE&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;STATE id="INTERMEDIATE"&gt;
 *		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *              &lt;MESSAGE id="MOVELEFT" action="moveLeft" nextState="STOP"/&gt;
 *		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 *              &lt;MESSAGE id="MOVERIGHT" action="moveRight" nextState="ANKIT"/&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;/STATE&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;STATE id="STOP"&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;/STATE&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;STATE id="ANKIT"&gt;
 *	&nbsp;&nbsp;&nbsp;&nbsp;&lt;/STATE&gt;
 * 	
 * &lt;/FSM&gt;
 * </p>
 * 
 * @version  0.01
 * @author ANKIT
 */
public class FSM implements java.io.Serializable {
    
    /* Added to support Serializability */
    private static final long serialVersionUID = -4817986591227138567L;
    
    /*
     * Any FSM requires three things:
     * * States
     * * Messages
     * * Actions
     */
    private FSMStates _states;
    private transient FSMAction _action;
    private transient Object _sharedData;
    
    /**
     * Constructor allows to create a FSM from a specified file-name
     * and specified Actions
     * 
     * @param configFName : Configuration file-name.
     * @param action      : Actions to be configured.
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    public FSM(String configFName, FSMAction action) 
            throws ParserConfigurationException, SAXException, IOException {
        this._states = new FSMStates(configFName, !"".equals(configFName));
        this._action = action;
    }
    
    /**
     * Constructor allows to create an FSM from a specified file-name
     * and specified Actions along with Shared Data
     * 
     * @param configFName : Configuration file-name.
     * @param action      : Actions to be configured.
     * @param sharedData  : Specify any shared data required.
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    public FSM(String configFName, FSMAction action, Object sharedData) 
            throws ParserConfigurationException, SAXException, IOException {
            this(configFName, action);
            this._sharedData = sharedData;
    }
    
    /**
     * Constructor takes the default configuration file
     * Shall not be used in production environment
     * 
     * @param action
     * Initial action associated with default/initial state
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    @Deprecated
    public FSM(FSMAction action) 
            throws ParserConfigurationException, SAXException, IOException {
        this("",action);
    }
    
    /**
     * Constructor takes the default configuration file
     * Shall not be used in production environment
     * 
     * @param action
     * Default action associated with initial state
     * 
     * @param sharedData
     * Data shared by another layer utilizing this FSM.
     * All actions shall also be invoked with this specified sharedData
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    @Deprecated
    public FSM(FSMAction action, Object sharedData) 
            throws ParserConfigurationException, SAXException, IOException {
        this(action);
        this._sharedData = sharedData;
    }
    
    /**
     * Constructor takes the default configuration file
     * Shall not be used in production environment
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    @Deprecated
    public FSM() 
            throws ParserConfigurationException, SAXException, IOException {
        this("",null);
    }

    /**
     * Constructor allows to create a FSM from a specified Input-Stream
     * and specified Actions along with Shared data
     * 
     * @param configFStream Input Stream of the XML Configuration file.
     * @param action    Specified actions for the given FSM
     * @param sharedData Shared Data passed across in FSM
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    public FSM(InputStream configFStream, FSMAction action, Object sharedData) 
            throws ParserConfigurationException, SAXException, IOException {
        this._states = new FSMStates(configFStream);
        this._action = action;
        this._sharedData = sharedData;
    }

    /**
     * Constructor allows to create a FSM from a specified Input-Stream
     * and specified Actions
     * 
     * @param configFStream Input Stream of the XML Configuration file.
     * @param action    Specified actions for the given FSM
     * 
     * @throws ParserConfigurationException
     * In case an error is encountered parsing XML Configuration file
     * 
     * @throws SAXException
     * In case an error is encountered in XML format
     * 
     * @throws IOException
     * In case an error is encountered in interacting with configuration file
     */
    public FSM(InputStream configFStream, FSMAction action) 
            throws ParserConfigurationException, SAXException, IOException {
        this(configFStream, action, null);
    }
    /**
     * Method on receiving the Message Id, takes appropriate action
     * and on successful execution of the action Transitions to the new-state
     * as per the transition map.
     * 
     * @param recvdMsgId Received Message Id
     * 
     * @return Returns the Current State as String
     */
    public Object ProcessFSM(String recvdMsgId) {
        Object _r;
        _r = this._states.getCurrentState().getNewTransitionMap().get(recvdMsgId);
        if ( null != _r) {
            String[] _t = new String[2];
            _t[0] = ((FSMTransitionInfo)_r).getActionName();
            _t[1] = ((FSMTransitionInfo)_r).getNextState();
            boolean status = true;
            for (Object _f: this._states.getAllStates()) {
                if( ((FSMState)_f).getCurrentState().equals((String)_t[1])) {
                    /* Check if the action specific to each message exists
                       If not, then in this case call the generic action function
                    */
                    FSMStateAction _a = ((FSMState)_f).getBeforeTransition();
                    if (_a!=null) {
                        _a.stateTransition(((FSMState)_f).getCurrentState(), 
                                this._sharedData);
                    }
                    
                    FSMAction act = ((FSMTransitionInfo)_r).getAction();
                    if (act!=null) {
                        /* If customized action is declared, call an entry function */
                        act.entry(this._states.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                        status = act.action(this._states.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                    } else if ( null != this._action) {
                        status = 
                        this._action.action(this._states.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                    }
                    
                    if(status) {
                        this._states.setCurrentState((FSMState)_f);
                        
                        if (act!=null) {
                            act.afterTransition(this._states.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                        }else if ( null != this._action) {
                            this._action.afterTransition(this._states.getCurrentState().getCurrentState(), 
                                    (String)_t[0], (String)_t[1], this._sharedData);
                        }
                    }

                    if (act!=null) {
                        /* Exit function called irrespective of transition status */
                        act.exit(this._states.getCurrentState().getCurrentState(), 
                                (String)_t[0], (String)_t[1], this._sharedData);
                    }
                    
                    FSMStateAction _b = ((FSMState)_f).getAfterTransition();
                    if (_b!=null) {
                        _b.stateTransition(((FSMState)_f).getCurrentState(), 
                                this._sharedData);
                    }
                    
                    break;
                }
            }
        }
        return _r;
    }

    /**
     * Method returns the current state of the FSM
     * 
     * @return Current state of the FSM
     */
    public String getCurrentState() { return this._states.getCurrentState().getCurrentState(); }
    
    /**
     * Method sets the shared data for the FSM
     * This method overwrites the previous shared data
     * 
     * @param data  Set shared data for the FSM.
     *              <b>Note:</b> Call to this function overwrites any previous shared data.
     */
    public void setShareData(Object data) { this._sharedData = data; }
    
    /**
     *
     * @param states
     * List of state for which action needs to be set
     * 
     * @param message
     * Message name which shall trigger the specified action
     * 
     * @param act
     * Action associated with the message
     */
    public void setAction(ArrayList<String> states, String message, 
            FSMAction act) {
        _states.setAction(states, message, act);
    }
    
    /**
     *
     * @param state
     * State for which action needs to be set
     * 
     * @param message
     * Message name which shall trigger the specified action
     * 
     * @param act
     * Action associated with the message
     */
    public void setAction(String state, String message, 
            FSMAction act) {
        setAction(new ArrayList(Arrays.asList(state)), message, act);
    }
    
    /**
     *
     * @param message
     * Message name which shall trigger the specified action
     * 
     * @param act
     * Action associated with the message
     */
    public void setAction(String message, FSMAction act) {
        _states.setAction(message, act);
    }

    public void setStatesBeforeTransition(String state, FSMStateAction act) {
        _states.setStateBeforeTransition(state, act);
    }
    
    public void setStatesBeforeTransition(ArrayList<String> states, 
            FSMStateAction act) {
        _states.setStateBeforeTransition(states, act);
    }

    public void setStatesBeforeTransition(FSMStateAction act) {
        ArrayList<String> l = null;
        _states.setStateBeforeTransition(l, act);
    }
    
    public void setStatesAfterTransition(String state, FSMStateAction act) {
        _states.setStateAfterTransition(state, act);
    }
    
    public void setStatesAfterTransition(ArrayList<String> states, 
            FSMStateAction act) {
        _states.setStateAfterTransition(states, act);
    }

    public void setStatesAfterTransition(FSMStateAction act) {
        ArrayList<String> l = null;
        _states.setStateAfterTransition(l, act);
    }

    /**
     * Method returns all states associated with the FSM
     * 
     * @return Returns all states of the FSM
     */
    public List getAllStates() { return _states.getAllStates(); }
 
    /**
     * 
     * @param act 
     * Default Action method for the FSM 
     */
    public void setDefaultFsmAction(FSMAction act) { _action = act; }    
}
