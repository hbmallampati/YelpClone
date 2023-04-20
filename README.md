# YelpClone

The aim of this project is to develop an application that extracts useful information from the Yelp dataset and provides a user-friendly interface for potential customers seeking businesses that match their search criteria. The application will allow users to search for businesses across various categories and sub-categories, using attributes associated with each category. Users will be able to view business reviews and information associated with each business category. The application will employ be faceted search, allowing users to filter search results using available business attributes. Facets such as category, sub-category, attributes, reviews, stars, and votes will be available for users to filter search results. When a user clicks on a facet value, the search results will be reduced to only the items that have that value.

#### MySQL Yelp database structure- 
<p align="center"><img width="440" alt="Screenshot 2023-04-20 at 3 48 09 PM" src="https://user-images.githubusercontent.com/98439391/233503005-b947ca79-3631-48c2-8368-e3d0c157ac3a.png">
</p>

#### Application launch screen:
<p align="center"><img width="1440" alt="Screenshot 2023-04-20 at 3 49 45 PM" src="https://user-images.githubusercontent.com/98439391/233504381-9c7a9e8c-05fa-4584-a0d9-1a9cc0051243.png">
</p>

#### Result 1: Businesses query
Query result for "find all businesses with category = Active Life/ Arts & Entertainment/ Automotive/ Doctors and with subcategory = Adult Education/ American(New)/ American(Traditional)/ Breakfast & Brunch" - 
<p align="center"><img width="1440" alt="Screenshot 2023-04-20 at 3 50 26 PM" src="https://user-images.githubusercontent.com/98439391/233504228-347bc8ed-e158-419a-b991-1262de7b8753.png">
</p>

#### Result 2: User query
Query result for "find all users yelping for businesses which belong to main category = Active Life/ Arts & Entertainment/ Automotive/ Doctors and with subcategory = Adult Education/ American(New)/ American(Traditional)/ Breakfast & Brunch" - 
<p align="center"><img width="1440" alt="Screenshot 2023-04-20 at 3 50 35 PM" src="https://user-images.githubusercontent.com/98439391/233504591-f3cdac76-c107-4003-9993-25741e7b46fc.png">
</p>

#### Result 3: Faceted search for users
Query result for "find all users yelping since forever, who have contributed more than 20 reviews to yelp community 'and' who have more than 10 friends 'and' whose average star rating is more than 4 'and' the number of votes the user has received is more than 10."
<p align="center"><img width="1440" alt="Screenshot 2023-04-20 at 3 51 49 PM" src="https://user-images.githubusercontent.com/98439391/233505026-88e8ec41-787c-41f4-ae32-d84acd02f2e7.png">
</p>
