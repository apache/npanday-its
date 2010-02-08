namespace NPanday.IT {

public class Lib {
    // test that we can have a compile-time dependency on a .netmodule
    private It0002 it;

    // used to test that a runtime dependency on a .netmodule works
    public string getItValue()
    {
        return it.getValue();
    }
}
}
