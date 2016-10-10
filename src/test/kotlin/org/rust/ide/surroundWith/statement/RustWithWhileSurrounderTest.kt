package org.rust.ide.surroundWith.statement

import org.rust.ide.surroundWith.RustSurrounderTestCaseBase
import org.rust.ide.surroundWith.statement.RustWithWhileSurrounder

class RustWithWhileSurrounderTest : RustSurrounderTestCaseBase(RustWithWhileSurrounder()) {
    fun testNotApplicable1() {
        doTestNotApplicable(
            """
            fn main() {
                let mut server <selection>= Nickel::new();
                server.get("**", hello_world);
                server.listen("127.0.0.1:6767").unwrap();</selection>
            }
            """
        )
    }

    fun testNotApplicable2() {
        doTestNotApplicable(
            """
            fn main() {
                <selection>#![cfg(test)]
                let mut server = Nickel::new();
                server.get("**", hello_world);
                server.listen("127.0.0.1:6767").unwrap();</selection>
            }
            """
        )
    }

    fun testApplicableComment() {
        doTest(
            """
            fn main() {
                <selection>// comment
                let mut server = Nickel::new();
                server.get("**", hello_world);
                server.listen("127.0.0.1:6767").unwrap(); // comment</selection>
            }
            """
            ,
            """
            fn main() {
                while <caret> {
                    // comment
                    let mut server = Nickel::new();
                    server.get("**", hello_world);
                    server.listen("127.0.0.1:6767").unwrap(); // comment
                }
            }
            """
        )
    }

    fun testSimple1() {
        doTest(
            """
            fn main() {
                <selection>let mut server = Nickel::new();
                server.get("**", hello_world);
                server.listen("127.0.0.1:6767").unwrap();</selection>
            }
            """
            ,
            """
            fn main() {
                while <caret> {
                    let mut server = Nickel::new();
                    server.get("**", hello_world);
                    server.listen("127.0.0.1:6767").unwrap();
                }
            }
            """
        )
    }

    fun testSimple2() {
        doTest(
            """
            fn main() {
                let mut server = Nickel::new();<selection>
                server.get("**", hello_world);
                server.listen("127.0.0.1:6767").unwrap();</selection>
            }
            """
            ,
            """
            fn main() {
                let mut server = Nickel::new();
                while <caret> {
                    server.get("**", hello_world);
                    server.listen("127.0.0.1:6767").unwrap();
                }
            }
            """
        )
    }

    fun testSimple3() {
        doTest(
            """
            fn main() {
                <selection>let mut server = Nickel::new();
                server.get("**", hello_world);</selection>
                server.listen("127.0.0.1:6767").unwrap();
            }
            """
            ,
            """
            fn main() {
                while <caret> {
                    let mut server = Nickel::new();
                    server.get("**", hello_world);
                }
                server.listen("127.0.0.1:6767").unwrap();
            }
            """
        )
    }

    fun testSingleLine() {
        doTest(
            """
            fn main() {
                let mut server = Nickel::new();
                server.get("**", hello_world)<caret>;
                server.listen("127.0.0.1:6767").unwrap();
            }
            """
            ,
            """
            fn main() {
                let mut server = Nickel::new();
                while <caret> {
                    server.get("**", hello_world);
                }
                server.listen("127.0.0.1:6767").unwrap();
            }
            """
        )
    }
}