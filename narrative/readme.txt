I decided to spend more than the suggested 2 hours on this as I just could not
bring myself to submit non-functioning code.  I balanced functionality and
completion time to result in a project that works in a limited capacity.  I also
wished to demonstrate my proficiency with Spring, designing thread-safe
concurrent code, and writing a unit test that actually validates something.  I
estimate I spent between 3 to 4, maybe 5 hours on this.

To run from the command line (assuming a *nix OS):

# mvn clean package && java -jar target/auction-1.0-SNAPSHOT.jar

then point your browser to

http://localhost:8080

Note that the UI is crude HTML (think Craigslist :) ), as I am up front about my
web UI skills being limited to just getting things to work, not necessarily
looking "pretty".  Of course if need be, and given more time, I would have
formatted the UI much better with bootstrap and jQuery, and displayed my table
of auction listings with the DataTables jQuery plugin.  Please also note my
comments throughout this project explaining some design decisions and known
issues I was willing to accept for the sake of completing this in a relatively
short time.

Thanks again!
