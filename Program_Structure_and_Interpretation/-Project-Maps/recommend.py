"""A Yelp-powered Restaurant Recommendation Program"""

from abstractions import *
from data import ALL_RESTAURANTS, CATEGORIES, USER_FILES, load_user_file
from ucb import main, trace, interact
from utils import distance, mean, zip, enumerate, sample
from visualize import draw_map

##################################
# Phase 2: Unsupervised Learning #
##################################


def find_closest(location, centroids):
    """Return the centroid in centroids that is closest to location.
    If multiple centroids are equally close, return the first one.

    >>> find_closest([3.0, 4.0], [[0.0, 0.0], [2.0, 3.0], [4.0, 3.0], [5.0, 5.0]])
    [2.0, 3.0]
    """
    # BEGIN Question 3
   
    # The x here is one element in centroids
    return min( centroids, key = lambda x: distance(location,x) )
   
    # END Question 3


def group_by_first(pairs):
    """Return a list of pairs that relates each unique key in the [key, value]
    pairs to a list of all values that appear paired with that key.

    Arguments:
    pairs -- a sequence of pairs

    >>> example = [ [1, 2], [3, 2], [2, 4], [1, 3], [3, 1], [1, 2] ]
    >>> group_by_first(example)
    [[2, 3, 2], [2, 1], [4]]
    """
    keys = []
    for key, _ in pairs:
        if key not in keys:
            keys.append(key)
    return [[y for x, y in pairs if x == key] for key in keys]


def group_by_centroid(restaurants, centroids):
    """Return a list of clusters, where each cluster contains all restaurants
    nearest to a corresponding centroid in centroids. Each item in
    restaurants should appear once in the result, along with the other
    restaurants closest to the same centroid.
    """
    # BEGIN Question 4

    # Each element in centroid_to_restaurant is a list that consists of [a centroid , its restaurant relation]
    # ie: [ [[0.0, 0.0], 'A'], [[4.0, 3.0], 'B'], [[0.0, 0.0], 'C'] ] 
    # where 'A','B' and 'C' are the NAMES of the restaurant for clarity (normally they would just be restaurant abstractions)
    centroid_to_restaurant = [ [find_closest(restaurant_location(r), centroids), r] for r in restaurants ] 
    # group_by_first gives you a LIST where each element is a cluster of restaurant abstractions
    # ie: [['A', 'C'], ['B']]
    return group_by_first(centroid_to_restaurant) 

    # END Question 4


def find_centroid(cluster):
    """Return the centroid of the locations of the restaurants in cluster."""
    # BEGIN Question 5
    
    # Location is the location of each restaurant abstraction in the cluster
    # ie: [[-3, -4], [1, -1], [2, -4]]
    locations = [restaurant_location(r) for r in cluster]
    X = mean( [x for x,y in locations] )
    Y = mean( [y for x,y in locations] )
    return [X, Y]
   
    # END Question 5


def k_means(restaurants, k, max_updates=100):
    """Use k-means to group restaurants by location into k clusters."""
    assert len(restaurants) >= k, 'Not enough restaurants to cluster'
    old_centroids, n = [], 0
    # Select initial centroids randomly by choosing k different restaurants
    centroids = [restaurant_location(r) for r in sample(restaurants, k)]

    while old_centroids != centroids and n < max_updates:
        old_centroids = centroids
        # BEGIN Question 6

        cluster_list = group_by_centroid(restaurants, centroids)
        centroids = [find_centroid(cluster) for cluster in cluster_list]
        
        # END Question 6
        n += 1
    return centroids


################################
# Phase 3: Supervised Learning #
################################


def find_predictor(user, restaurants, feature_fn):
    """Return a rating predictor (a function from restaurants to ratings),
    for a user by performing least-squares linear regression using feature_fn
    on the items in restaurants. Also, return the R^2 value of this model.

    Arguments:
    user -- A user
    restaurants -- A sequence of restaurants
    feature_fn -- A function that takes a restaurant and returns a number
    """
    reviews_by_user = {review_restaurant_name(review): review_rating(review)
                       for review in user_reviews(user).values()}

    # the extracted values for each restaurant in restaurants
    xs = [feature_fn(r) for r in restaurants]
    # user's ratings for the restaurants in restaurants
    ys = [reviews_by_user[restaurant_name(r)] for r in restaurants]

    # BEGIN Question 7
    
    # pre-calculated means since it will be used multiple times in list comprehensions
    mean_x, mean_y = mean(xs), mean(ys)
    # create 2 lists of common elements that will be used in summation definition later
    s_x , s_y = [(x-mean_x) for x in xs], [(y-mean_y) for y in ys]

    # sum list s_x and s_y where each element is squared
    S_xx = sum( [x**2 for x in s_x] )
    S_yy = sum( [y**2 for y in s_y] )

    # Create a list where each element is the product of 1 element in s_x and 1 element in s_y
    S_xy = sum( [x*y for x,y in zip(s_x, s_y)] )

    # Assign values
    b = S_xy / S_xx
    a = mean_y - (b*mean_x)
    r_squared = (S_xy**2) / (S_xx * S_yy)
    
    # END Question 7

    def predictor(restaurant):
        return b * feature_fn(restaurant) + a

    return predictor, r_squared


def best_predictor(user, restaurants, feature_fns):
    """Find the feature within feature_fns that gives the highest R^2 value
    for predicting ratings by the user; return a predictor using that feature.

    Arguments:
    user -- A user
    restaurants -- A list of restaurants
    feature_fns -- A sequence of functions that each takes a restaurant
    """
    reviewed = user_reviewed_restaurants(user, restaurants)
    # BEGIN Question 8

    # Create a list of all predictors and r_squared values associated with feature_fns
    predictor_list = [find_predictor(user, reviewed, fn) for fn in feature_fns]
    # Find the highest r_squared and its associated predictor function.
    best_predictor_pair = max(predictor_list, key = lambda x: x[1])
    # return the function in the pair
    return best_predictor_pair[0]

    # END Question 8


def rate_all(user, restaurants, feature_fns):
    """Return the predicted ratings of restaurants by user using the best
    predictor based on a function from feature_fns.

    Arguments:
    user -- A user
    restaurants -- A list of restaurants
    feature_fns -- A sequence of feature functions
    """
    predictor = best_predictor(user, ALL_RESTAURANTS, feature_fns)
    reviewed = user_reviewed_restaurants(user, restaurants)
    # BEGIN Question 9

    not_reviewed = [r for r in restaurants if r not in reviewed]
    ratings = {restaurant_name(r):predictor(r) for r in not_reviewed}
    for i in reviewed:
        # Personal Note: recall how to add key:values to an already defined dictionary
        ratings[restaurant_name(i)] = user_rating(user, restaurant_name(i))
    return ratings
    
    # END Question 9


def search(query, restaurants):
    """Return each restaurant in restaurants that has query as a category.

    Arguments:
    query -- A string
    restaurants -- A sequence of restaurants
    """
    # BEGIN Question 10

    return[x for x in restaurants if query in restaurant_categories(x)]
    
    # END Question 10


def feature_set():
    """Return a sequence of feature functions."""
    return [lambda r: mean(restaurant_ratings(r)),
            restaurant_price,
            lambda r: len(restaurant_ratings(r)),
            lambda r: restaurant_location(r)[0],
            lambda r: restaurant_location(r)[1]]

@main
def main(*args):
    import argparse
    parser = argparse.ArgumentParser(
        description='Run Recommendations',
        formatter_class=argparse.RawTextHelpFormatter
    )
    parser.add_argument('-u', '--user', type=str, choices=USER_FILES,
                        default='test_user',
                        metavar='USER',
                        help='user file, e.g.\n' +
                        '{{{}}}'.format(','.join(sample(USER_FILES, 3))))
    parser.add_argument('-k', '--k', type=int, help='for k-means')
    parser.add_argument('-q', '--query', choices=CATEGORIES,
                        metavar='QUERY',
                        help='search for restaurants by category e.g.\n'
                        '{{{}}}'.format(','.join(sample(CATEGORIES, 3))))
    parser.add_argument('-p', '--predict', action='store_true',
                        help='predict ratings for all restaurants')
    parser.add_argument('-r', '--restaurants', action='store_true',
                        help='outputs a list of restaurant names')
    args = parser.parse_args()

    # Output a list of restaurant names
    if args.restaurants:
        print('Restaurant names:')
        for restaurant in sorted(ALL_RESTAURANTS, key=restaurant_name):
            print(repr(restaurant_name(restaurant)))
        exit(0)

    # Select restaurants using a category query
    if args.query:
        restaurants = search(args.query, ALL_RESTAURANTS)
    else:
        restaurants = ALL_RESTAURANTS

    # Load a user
    assert args.user, 'A --user is required to draw a map'
    user = load_user_file('{}.dat'.format(args.user))

    # Collect ratings
    if args.predict:
        ratings = rate_all(user, restaurants, feature_set())
    else:
        restaurants = user_reviewed_restaurants(user, restaurants)
        names = [restaurant_name(r) for r in restaurants]
        ratings = {name: user_rating(user, name) for name in names}

    # Draw the visualization
    if args.k:
        centroids = k_means(restaurants, min(args.k, len(restaurants)))
    else:
        centroids = [restaurant_location(r) for r in restaurants]
    draw_map(centroids, restaurants, ratings)