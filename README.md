# AWSSimpleWebsite

AWSSimpleWebsite is a Tomcat Website (Java Servlets) that can simulate CPU loads on EC2 instances and see your ElasticLoadBalancer and AutoScalingGroups in action! Genereate a .war deployable file and deploy the site to your EC2 instances with a running Tomcat instance.



##How does it work?

On start-up the LoadGeneratorServlet servlet will randomly select a latency value from 100ms to 5000ms. This is the time the Servlet will take to respond to a client request. During this time the Servlet will sleep for some time and consume CPU for another chunk of time. The larger the TPS the larger the latency time used towards CPU consumption. At some point you might notice 100% of CPU consumption! If not, increase the TPS load on the client side. 

##AWS ElasticLoadBalancer in Action

Write a script to load the LoadGeneratorServlet servlet and produce TPS or use the AWSSimpleWebsiteLoadTest tool (check my GitHub account) for that purpose. While load is being generated open your browser and hit the link /AWSSimpleWebsite/LoadGeneratorServlet. If you have multiple EC2 instances in your ASG you'll notice that the "#requests processed" value returned by the Servlet will not the same for all EC2s (refresh the page a few times). Instead, the higher the latency the lower the #requests being directed to that instance. This is the ELB in action distributing your requests fairly across your EC2 instance! 

##AWS AutoScalingGroup in Action

Create CloudWatch alarms based on CPU usage and use them in your ASG scaling policies. Produce a high TPS load against the LoadGeneratorServlet servlet and notice a few minutes later (depending on your alarm settings) your ASG launching extra instances to keep up with the load. Reduce the TPS and notice the ASG scaling down your cluster of EC2s. This is the ASG in action automatically scaling up and down your group of EC2 instances 

