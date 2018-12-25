# Test utilities for Scalecube

As a test developer I want to have the ability to test some components in multiple types of environment.

Some environments would be with mocks
Some would mock only data-layers and create all components in this test JVM.
Some environments would be as docker containers plus real machines hybrid.

**You name it!!!**

But as the tests writer - I truly don't care. 

So what's the plan?

## Let's split environments and tests 

### First goes the test:

 