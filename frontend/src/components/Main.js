import { useState, useEffect } from "react";

export default function Main({ prop1, prop2 }) { // props are data passed from the parent component
    // state is data that makes every component update when it changes
    const [state, setState] = useState(prop1);

    // setState is the function that changes the state
    const changeState = () => {
        if(state === prop1) setState(prop2);
        else setState(prop1);
    }

    // useEffect is used for functions that need to run
    // every time some state changes or when the component first renders
    useEffect(() => {
        console.log("state changed");
    }, [state]); // dependency array (this executes when state changes)

    return (<main className="main-component">
        <p>Hello World!</p>
        <p>{ state }</p>
        <button onClick={() => changeState()}>Change State</button>
    </main>);

}