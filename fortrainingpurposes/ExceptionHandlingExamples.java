import javax.servlet.ServletException;

class ExceptionHandlingExamples { // https://www.ibm.com/support/pages/best-practice-catching-and-re-throwing-java-exceptions
    void worst() {
        // #1. Worst -- there is no indication that an exception
        // occurred and processing continues.

        try {
            // do work
        } catch (Throwable t) {
        }
    }

    void veryBad() {
        // #2. Very Bad -- there is an indication that an
        // exception occurred but there is no stack trace, and
        // processing continues.

        try {
            // do work
        } catch (Throwable t) {
            System.err.println("Error: " + t.getMessage());
        }
    }

    void incorrect() {
        // #3. Incorrect. The stack trace of the original
        // exception is lost. In the case of an Exception such as
        // a NullPointerException, getMessage() will return a
        // blank string, so there will be little indication of
        // the problem.

        try {
            // do work
        } catch (Throwable t) {
            throw new ServletException("Error: " + t.getMessage());
        }
    }

    void correct1() {
        // #4. Correct. Note the exception, t, is passed as the second
        // parameter to the ServletException constructor.

        try {
            // do work
        } catch (Throwable t) {
            throw new ServletException("Error: " + t.getMessage(), t);
        }
    }

    void correct2() {
        // #5. Also correct.

        try {
            // do work
        } catch (Throwable t) {
            try {
                // Perform some application logging or auditing
                // e.g. t.printStackTrace();
            } catch (Throwable tAppDebug) {
                tAppDebug.printStackTrace();
                throw t;
            }
        }
    }
}