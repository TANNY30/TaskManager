# Task manager

[![Gradle][1]][2]



[1]:https://www.google.com/url?sa=i&url=https%3A%2F%2Fgradle.com%2Fbrand%2F&psig=AOvVaw1bfuJJaqno5cdERv4LhvMC&ust=1687370759427000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCNiIrYe40v8CFQAAAAAdAAAAABAF
[2]:https://gradle.org/releases/







## serving below requests
:avocado:       POST
:corn:          GET all Tasks
:hot_pepper:    GET by ID
:onion:         PATCH dueDate and completed status
:peanuts:       DELETE Task

:stop_sign:     Bad request response for dates {<} today's date
:construction:  Bad request response for name {<} 4 and name {>} 100 characters         