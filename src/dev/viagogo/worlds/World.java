package dev.viagogo.worlds;

import dev.viagogo.dev.viagogo.events.Event;
import dev.viagogo.worlds.distancemetrics.DistanceMetric;

import java.util.*;
import java.util.stream.Collectors;

public class World {

    private DistanceMetric distanceMetric;
    private Map<Point, Event> events;

    public World(DistanceMetric distanceMetric) {
        this.distanceMetric = distanceMetric;
        events = new HashMap<>();
    }

    public void setDistanceMetric(DistanceMetric distanceMetric) {
        this.distanceMetric = distanceMetric;
    }

    public void addEventAt(Event event, Point point) {
        events.put(point, event);
    }

    public List<Event> getClosestEvents(Point point, int n) {
        // assuming you want to return all up to a number if the world doesn't contain enough points.
        int numToReturn = n < events.size() ? n : events.size();
        // sort the points based on distance from the point provided, not each other.
        return events.keySet().stream().sorted((p1, p2) -> {
            int dist1 = distanceMetric.distanceBetween(p1, point);
            int dist2 = distanceMetric.distanceBetween(p2, point);
            return Integer.compare(dist1, dist2);
        }).limit(numToReturn) // we only want n results (e.g. 5 closest)
                .map(events::get) // get the corresponding event happening at that point
                .collect(Collectors.toList()); // give them back as a list.
    }
}