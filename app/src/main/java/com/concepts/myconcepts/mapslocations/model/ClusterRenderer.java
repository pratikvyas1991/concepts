package com.concepts.myconcepts.mapslocations.model;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.concepts.myconcepts.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.projection.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm.MAX_DISTANCE_AT_ZOOM;

/**
 * Created by tasol on 26/3/18.
 */

public class ClusterRenderer implements com.google.maps.android.clustering.view.ClusterRenderer<Users> {
    private static final boolean SHOULD_ANIMATE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    private final GoogleMap mMap;
    private final IconGenerator mIconGenerator;
    private final ClusterManager<Users> mClusterManager;
    private final float mDensity;
    private boolean mAnimate;
    public static Context mContext;
    List<Users> patiends = new ArrayList<>();
    boolean addMarkerNumber = false;
    public Marker selectedMarker;

    public static int setSelectedMarker;

    int markerFlag = 0;
    public static String TAG1 = "@@@###@#@$@";
    public static String TAG2 = "$$$$$###$$$";

    private static final int[] BUCKETS = {10, 20, 50, 100, 200, 500, 1000};
    private ShapeDrawable mColoredCircleBackground;

    /**
     * Markers that are currently on the map.
     */
    private Set<MarkerWithPosition> mMarkers = Collections.newSetFromMap(
            new ConcurrentHashMap<MarkerWithPosition, Boolean>());

    /**
     * Icons for each bucket.
     */
    private SparseArray<BitmapDescriptor> mIcons = new SparseArray<BitmapDescriptor>();

    /**
     * Markers for single ClusterItems.
     */
    private MarkerCache<Users> mMarkerCache = new MarkerCache<Users>();

    /**
     * If cluster size is less than this size, display individual markers.
     */
    private int mMinClusterSize = 4;

    /**
     * The currently displayed set of clusters.
     */
    private Set<? extends Cluster<Users>> mClusters;

    /**
     * Lookup between markers and the associated cluster.
     */
    private Map<Marker, Cluster<Users>> mMarkerToCluster = new HashMap<Marker, Cluster<Users>>();
    private Map<Cluster<Users>, Marker> mClusterToMarker = new HashMap<Cluster<Users>, Marker>();

    /**
     * The target zoom level for the current set of clusters.
     */
    private float mZoom;

    private final ViewModifier mViewModifier = new ViewModifier();

    private ClusterManager.OnClusterClickListener<Users> mClickListener;
    private ClusterManager.OnClusterInfoWindowClickListener<Users> mInfoWindowClickListener;
    private ClusterManager.OnClusterItemClickListener<Users> mItemClickListener;
    private ClusterManager.OnClusterItemInfoWindowClickListener<Users> mItemInfoWindowClickListener;
    private String TAG = "@@@###$$$DefaultCluster Renderer : ";

    public ClusterRenderer(Context context, GoogleMap map, ClusterManager<Users> clusterManager, boolean canAddMarkerNumber, List<Users> patiends) {
        this.patiends = patiends;
        mMap = map;
        mAnimate = true;
        mContext = context;
        addMarkerNumber = canAddMarkerNumber;
        mDensity = context.getResources().getDisplayMetrics().density;
        mIconGenerator = new IconGenerator(context);
        mIconGenerator.setContentView(makeSquareTextView(context));
        mIconGenerator.setTextAppearance(R.style.amu_ClusterIcon_TextAppearance);
        mIconGenerator.setBackground(makeClusterBackground());
        mClusterManager = clusterManager;
    }

    @Override
    public void onAdd() {
        mClusterManager.getMarkerCollection().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return mItemClickListener != null && mItemClickListener.onClusterItemClick(mMarkerCache.get(marker));
            }
        });

        mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (mItemInfoWindowClickListener != null) {
                    mItemInfoWindowClickListener.onClusterItemInfoWindowClick(mMarkerCache.get(marker));
                }
            }
        });

        mClusterManager.getClusterMarkerCollection().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return mClickListener != null && mClickListener.onClusterClick(mMarkerToCluster.get(marker));
            }
        });

        mClusterManager.getClusterMarkerCollection().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (mInfoWindowClickListener != null) {
                    mInfoWindowClickListener.onClusterInfoWindowClick(mMarkerToCluster.get(marker));
                }
            }
        });
    }

    @Override
    public void onRemove() {
        mClusterManager.getMarkerCollection().setOnMarkerClickListener(null);
        mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(null);
        mClusterManager.getClusterMarkerCollection().setOnMarkerClickListener(null);
        mClusterManager.getClusterMarkerCollection().setOnInfoWindowClickListener(null);
    }

    private LayerDrawable makeClusterBackground() {
        mColoredCircleBackground = new ShapeDrawable(new OvalShape());
        ShapeDrawable outline = new ShapeDrawable(new OvalShape());
        outline.getPaint().setColor(0x80ffffff); // Transparent white.
        LayerDrawable background = new LayerDrawable(new Drawable[]{outline, mColoredCircleBackground});
        int strokeWidth = (int) (mDensity * 3);
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        return background;
    }

    private SquareTextView makeSquareTextView(Context context) {
        SquareTextView squareTextView = new SquareTextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        squareTextView.setLayoutParams(layoutParams);
        squareTextView.setId(R.id.amu_text);
        int twelveDpi = (int) (12 * mDensity);
        squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi);
        return squareTextView;
    }

    protected int getColor(int clusterSize) {
        final float hueRange = 220;
        final float sizeRange = 300;
        final float size = Math.min(clusterSize, sizeRange);
        final float hue = (sizeRange - size) * (sizeRange - size) / (sizeRange * sizeRange) * hueRange;
        return Color.HSVToColor(new float[]{
                hue, 1f, .6f
        });
    }

    protected String getClusterText(int bucket) {
        if (bucket < BUCKETS[0]) {
            return String.valueOf(bucket);
        }
        return String.valueOf(bucket) + "+";
    }

    /**
     * Gets the "bucket" for a particular cluster. By default, uses the number of points within the
     * cluster, bucketed to some set points.
     */
    protected int getBucket(Cluster<Users> cluster) {
        int size = cluster.getSize();
        if (size <= BUCKETS[0]) {
            return size;
        }
        for (int i = 0; i < BUCKETS.length - 1; i++) {
            if (size < BUCKETS[i + 1]) {
                return BUCKETS[i];
            }
        }
        return BUCKETS[BUCKETS.length - 1];
    }

    public int getMinClusterSize() {
        return mMinClusterSize;
    }

    public void setMinClusterSize(int minClusterSize) {
        mMinClusterSize = minClusterSize;
    }

    /**
     * ViewModifier ensures only one re-rendering of the view occurs at a time, and schedules
     * re-rendering, which is performed by the RenderTask.
     */
    @SuppressLint("HandlerLeak")
    private class ViewModifier extends Handler {
        private static final int RUN_TASK = 0;
        private static final int TASK_FINISHED = 1;
        private boolean mViewModificationInProgress = false;
        private RenderTask mNextClusters = null;

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TASK_FINISHED) {
                mViewModificationInProgress = false;
                if (mNextClusters != null) {
                    // Run the task that was queued up.
                    sendEmptyMessage(RUN_TASK);
                }
                return;
            }
            removeMessages(RUN_TASK);

            if (mViewModificationInProgress) {
                // Busy - wait for the callback.
                return;
            }

            if (mNextClusters == null) {
                // Nothing to do.
                return;
            }
            Projection projection = mMap.getProjection();

            RenderTask renderTask;
            synchronized (this) {
                renderTask = mNextClusters;
                mNextClusters = null;
                mViewModificationInProgress = true;
            }

            renderTask.setCallback(new Runnable() {
                @Override
                public void run() {
                    sendEmptyMessage(TASK_FINISHED);
                }
            });
            renderTask.setProjection(projection);
            renderTask.setMapZoom(mMap.getCameraPosition().zoom);
            new Thread(renderTask).start();
        }

        public void queue(Set<? extends Cluster<Users>> clusters) {
            synchronized (this) {
                // Overwrite any pending cluster tasks - we don't care about intermediate states.
                mNextClusters = new RenderTask(clusters);
            }
            sendEmptyMessage(RUN_TASK);
        }
    }

    /**
     * Determine whether the cluster should be rendered as individual markers or a cluster.
     */
    protected boolean shouldRenderAsCluster(Cluster<Users> cluster) {
        return cluster.getSize() > mMinClusterSize;
    }

    /**
     * Transforms the current view (represented by DefaultClusterRenderer.mClusters and DefaultClusterRenderer.mZoom) to a
     * new zoom level and set of clusters.
     * <p/>
     * This must be run off the UI thread. Work is coordinated in the RenderTask, then queued up to
     * be executed by a MarkerModifier.
     * <p/>
     * There are three stages for the render:
     * <p/>
     * 1. Markers are added to the map
     * <p/>
     * 2. Markers are animated to their final position
     * <p/>
     * 3. Any old markers are removed from the map
     * <p/>
     * When zooming in, markers are animated out from the nearest existing cluster. When zooming
     * out, existing clusters are animated to the nearest new cluster.
     */
    private class RenderTask implements Runnable {
        final Set<? extends Cluster<Users>> clusters;
        private Runnable mCallback;
        private Projection mProjection;
        private SphericalMercatorProjection mSphericalMercatorProjection;
        private float mMapZoom;

        private RenderTask(Set<? extends Cluster<Users>> clusters) {
            this.clusters = clusters;
        }

        /**
         * A callback to be run when all work has been completed.
         *
         * @param callback
         */
        public void setCallback(Runnable callback) {
            mCallback = callback;
        }

        public void setProjection(Projection projection) {
            this.mProjection = projection;
        }

        public void setMapZoom(float zoom) {
            this.mMapZoom = zoom;
            this.mSphericalMercatorProjection = new SphericalMercatorProjection(256 * Math.pow(2, Math.min(zoom, mZoom)));
        }

        @SuppressLint("NewApi")
        public void run() {
            if (clusters.equals(ClusterRenderer.this.mClusters)) {
                mCallback.run();
                return;
            }

            final MarkerModifier markerModifier = new MarkerModifier();

            final float zoom = mMapZoom;
            final boolean zoomingIn = zoom > mZoom;
            final float zoomDelta = zoom - mZoom;

            final Set<MarkerWithPosition> markersToRemove = mMarkers;
            final LatLngBounds visibleBounds = mProjection.getVisibleRegion().latLngBounds;
            // TODO: Add some padding, so that markers can animate in from off-screen.

            // Find all of the existing clusters that are on-screen. These are candidates for
            // markers to animate from.
            List<Point> existingClustersOnScreen = null;
            if (ClusterRenderer.this.mClusters != null && SHOULD_ANIMATE) {
                existingClustersOnScreen = new ArrayList<Point>();
                for (Cluster<Users> c : ClusterRenderer.this.mClusters) {
                    if (shouldRenderAsCluster(c) && visibleBounds.contains(c.getPosition())) {
                        Point point = mSphericalMercatorProjection.toPoint(c.getPosition());
                        existingClustersOnScreen.add(point);
                    }
                }
            }

            // Create the new markers and animate them to their new positions.
            final Set<MarkerWithPosition> newMarkers = Collections.newSetFromMap(
                    new ConcurrentHashMap<MarkerWithPosition, Boolean>());
            for (Cluster<Users> c : clusters) {
                boolean onScreen = visibleBounds.contains(c.getPosition());
                if (zoomingIn && onScreen && SHOULD_ANIMATE) {
                    Point point = mSphericalMercatorProjection.toPoint(c.getPosition());
                    Point closest = findClosestCluster(existingClustersOnScreen, point);
                    if (closest != null && mAnimate) {
                        LatLng animateTo = mSphericalMercatorProjection.toLatLng(closest);
                        markerModifier.add(true, new CreateMarkerTask(c, newMarkers, animateTo));
                    } else {
                        markerModifier.add(true, new CreateMarkerTask(c, newMarkers, null));
                    }
                } else {
                    markerModifier.add(onScreen, new CreateMarkerTask(c, newMarkers, null));
                }
            }

            // Wait for all markers to be added.
            markerModifier.waitUntilFree();

            // Don't remove any markers that were just added. This is basically anything that had
            // a hit in the MarkerCache.
            markersToRemove.removeAll(newMarkers);

            // Find all of the new clusters that were added on-screen. These are candidates for
            // markers to animate from.
            List<Point> newClustersOnScreen = null;
            if (SHOULD_ANIMATE) {
                newClustersOnScreen = new ArrayList<Point>();
                for (Cluster<Users> c : clusters) {
                    if (shouldRenderAsCluster(c) && visibleBounds.contains(c.getPosition())) {
                        Point p = mSphericalMercatorProjection.toPoint(c.getPosition());
                        newClustersOnScreen.add(p);
                    }
                }
            }

            // Remove the old markers, animating them into clusters if zooming out.
            for (final MarkerWithPosition marker : markersToRemove) {
                boolean onScreen = visibleBounds.contains(marker.position);
                // Don't animate when zooming out more than 3 zoom levels.
                // TODO: drop animation based on speed of device & number of markers to animate.
                if (!zoomingIn && zoomDelta > -3 && onScreen && SHOULD_ANIMATE) {
                    final Point point = mSphericalMercatorProjection.toPoint(marker.position);
                    final Point closest = findClosestCluster(newClustersOnScreen, point);
                    if (closest != null && mAnimate) {
                        LatLng animateTo = mSphericalMercatorProjection.toLatLng(closest);
                        markerModifier.animateThenRemove(marker, marker.position, animateTo);
                    } else {
                        markerModifier.remove(true, marker.marker);
                    }
                } else {
                    markerModifier.remove(onScreen, marker.marker);
                }
            }

            markerModifier.waitUntilFree();

            mMarkers = newMarkers;
            ClusterRenderer.this.mClusters = clusters;
            mZoom = zoom;

            mCallback.run();
        }
    }

    @Override
    public void onClustersChanged(Set<? extends Cluster<Users>> clusters) {
        mViewModifier.queue(clusters);
    }

    @Override
    public void setOnClusterClickListener(ClusterManager.OnClusterClickListener<Users> listener) {
        mClickListener = listener;
    }

    @Override
    public void setOnClusterInfoWindowClickListener(ClusterManager.OnClusterInfoWindowClickListener<Users> listener) {
        mInfoWindowClickListener = listener;
        Log.v(TAG2," onClusterInfoWindowClicked");
    }

    @Override
    public void setOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<Users> listener) {
        mItemClickListener = listener;
        Log.v(TAG2," onClusterItemClicked ");
    }

    @Override
    public void setOnClusterItemInfoWindowClickListener(ClusterManager.OnClusterItemInfoWindowClickListener<Users> listener) {
        mItemInfoWindowClickListener = listener;
    }

    @Override
    public void setAnimation(boolean animate) {
        mAnimate = animate;
    }

    private static double distanceSquared(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }

    private static Point findClosestCluster(List<Point> markers, Point point) {
        if (markers == null || markers.isEmpty()) return null;

        // TODO: make this configurable.
        double minDistSquared = MAX_DISTANCE_AT_ZOOM * MAX_DISTANCE_AT_ZOOM;
        Point closest = null;
        for (Point candidate : markers) {
            double dist = distanceSquared(candidate, point);
            if (dist < minDistSquared) {
                closest = candidate;
                minDistSquared = dist;
            }
        }
        return closest;
    }

    /**
     * Handles all markerWithPosition manipulations on the map. Work (such as adding, removing, or
     * animating a markerWithPosition) is performed while trying not to block the rest of the app's
     * UI.
     */
    @SuppressLint("HandlerLeak")
    private class MarkerModifier extends Handler implements MessageQueue.IdleHandler {
        private static final int BLANK = 0;

        private final Lock lock = new ReentrantLock();
        private final Condition busyCondition = lock.newCondition();

        private Queue<CreateMarkerTask> mCreateMarkerTasks = new LinkedList<CreateMarkerTask>();
        private Queue<CreateMarkerTask> mOnScreenCreateMarkerTasks = new LinkedList<CreateMarkerTask>();
        private Queue<Marker> mRemoveMarkerTasks = new LinkedList<Marker>();
        private Queue<Marker> mOnScreenRemoveMarkerTasks = new LinkedList<Marker>();
        private Queue<AnimationTask> mAnimationTasks = new LinkedList<AnimationTask>();

        /**
         * Whether the idle listener has been added to the UI thread's MessageQueue.
         */
        private boolean mListenerAdded;

        private MarkerModifier() {
            super(Looper.getMainLooper());
        }

        /**
         * Creates markers for a cluster some time in the future.
         *
         * @param priority whether this operation should have priority.
         */
        public void add(boolean priority, CreateMarkerTask c) {
            lock.lock();
            sendEmptyMessage(BLANK);
            if (priority) {
                mOnScreenCreateMarkerTasks.add(c);
            } else {
                mCreateMarkerTasks.add(c);
            }
            lock.unlock();
        }

        /**
         * Removes a markerWithPosition some time in the future.
         *
         * @param priority whether this operation should have priority.
         * @param m        the markerWithPosition to remove.
         */
        public void remove(boolean priority, Marker m) {
            lock.lock();
            sendEmptyMessage(BLANK);
            if (priority) {
                mOnScreenRemoveMarkerTasks.add(m);
            } else {
                mRemoveMarkerTasks.add(m);
            }
            lock.unlock();
        }

        /**
         * Animates a markerWithPosition some time in the future.
         *
         * @param marker the markerWithPosition to animate.
         * @param from   the position to animate from.
         * @param to     the position to animate to.
         */
        public void animate(MarkerWithPosition marker, LatLng from, LatLng to) {
            lock.lock();
            mAnimationTasks.add(new AnimationTask(marker, from, to));
            lock.unlock();
        }

        /**
         * Animates a markerWithPosition some time in the future, and removes it when the animation
         * is complete.
         *
         * @param marker the markerWithPosition to animate.
         * @param from   the position to animate from.
         * @param to     the position to animate to.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void animateThenRemove(MarkerWithPosition marker, LatLng from, LatLng to) {
            lock.lock();
            AnimationTask animationTask = new AnimationTask(marker, from, to);
            animationTask.removeOnAnimationComplete(mClusterManager.getMarkerManager());
            mAnimationTasks.add(animationTask);
            lock.unlock();
        }

        @Override
        public void handleMessage(Message msg) {
            if (!mListenerAdded) {
                Looper.myQueue().addIdleHandler(this);
                mListenerAdded = true;
            }
            removeMessages(BLANK);

            lock.lock();
            try {

                // Perform up to 10 tasks at once.
                // Consider only performing 10 remove tasks, not adds and animations.
                // Removes are relatively slow and are much better when batched.
                for (int i = 0; i < 10; i++) {
                    performNextTask();
                }

                if (!isBusy()) {
                    mListenerAdded = false;
                    Looper.myQueue().removeIdleHandler(this);
                    // Signal any other threads that are waiting.
                    busyCondition.signalAll();
                } else {
                    // Sometimes the idle queue may not be called - schedule up some work regardless
                    // of whether the UI thread is busy or not.
                    // TODO: try to remove this.
                    sendEmptyMessageDelayed(BLANK, 10);
                }
            } finally {
                lock.unlock();
            }
        }

        /**
         * Perform the next task. Prioritise any on-screen work.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void performNextTask() {
            if (!mOnScreenRemoveMarkerTasks.isEmpty()) {
                removeMarker(mOnScreenRemoveMarkerTasks.poll());
            } else if (!mAnimationTasks.isEmpty()) {
                mAnimationTasks.poll().perform();
            } else if (!mOnScreenCreateMarkerTasks.isEmpty()) {
                mOnScreenCreateMarkerTasks.poll().perform(this);
            } else if (!mCreateMarkerTasks.isEmpty()) {
                mCreateMarkerTasks.poll().perform(this);
            } else if (!mRemoveMarkerTasks.isEmpty()) {
                removeMarker(mRemoveMarkerTasks.poll());
            }
        }

        private void removeMarker(Marker m) {
            Cluster<Users> cluster = mMarkerToCluster.get(m);
            mClusterToMarker.remove(cluster);
            mMarkerCache.remove(m);
            mMarkerToCluster.remove(m);
            mClusterManager.getMarkerManager().remove(m);
        }

        /**
         * @return true if there is still work to be processed.
         */
        public boolean isBusy() {
            try {
                lock.lock();
                return !(mCreateMarkerTasks.isEmpty() && mOnScreenCreateMarkerTasks.isEmpty() &&
                        mOnScreenRemoveMarkerTasks.isEmpty() && mRemoveMarkerTasks.isEmpty() &&
                        mAnimationTasks.isEmpty()
                );
            } finally {
                lock.unlock();
            }
        }

        /**
         * Blocks the calling thread until all work has been processed.
         */
        public void waitUntilFree() {
            while (isBusy()) {
                // Sometimes the idle queue may not be called - schedule up some work regardless
                // of whether the UI thread is busy or not.
                // TODO: try to remove this.
                sendEmptyMessage(BLANK);
                lock.lock();
                try {
                    if (isBusy()) {
                        busyCondition.await();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public boolean queueIdle() {
            // When the UI is not busy, schedule some work.
            sendEmptyMessage(BLANK);
            return true;
        }
    }

    /**
     * A cache of markers representing individual ClusterItems.
     */
    private static class MarkerCache<Users> {
        private Map<Users, Marker> mCache = new HashMap<Users, Marker>();
        private Map<Marker, Users> mCacheReverse = new HashMap<Marker, Users>();

        public Marker get(Users item) {
            return mCache.get(item);
        }

        public Users get(Marker m) {
            return mCacheReverse.get(m);
        }

        public void put(Users item, Marker m) {
            mCache.put(item, m);
            mCacheReverse.put(m, item);
        }

        public void remove(Marker m) {
            Users item = mCacheReverse.get(m);
            mCacheReverse.remove(m);
            mCache.remove(item);
        }
    }

    /**
     * Called before the marker for a ClusterItem is added to the map.
     */
    protected void onBeforeClusterItemRendered(Users person, MarkerOptions markerOptions, int flag) {

        if (person != null) {

            BitmapDescriptor markerDescriptor = null;
            Bitmap numberedMarker = null;

            numberedMarker = setMarkerDrawable(person);

            markerDescriptor = BitmapDescriptorFactory.fromBitmap(numberedMarker);

            markerOptions.icon(markerDescriptor).snippet(person.getTitle());
        }

//        if (setSelectedLAT != null) {
//
//            if (person != null) {
//                if (setSelectedLONG.equalsIgnoreCase(person.getLongitude()) && setSelectedLAT.equalsIgnoreCase(person.getLatitude())) {
//                    BitmapDescriptor markerDescriptor = null;
//                    Bitmap numberedMarker = null;
//                    numberedMarker = setMarkerDrawable(true, person.getPatientID());
//                    markerDescriptor = BitmapDescriptorFactory.fromBitmap(numberedMarker);
//                    markerOptions.icon(markerDescriptor).snippet(person.getPatientName());
//
//                } else {
//                    BitmapDescriptor markerDescriptor = null;
//                    Bitmap numberedMarker = null;
//                    numberedMarker = setMarkerDrawable(false, person.getPatientID());
//
//                    markerDescriptor = BitmapDescriptorFactory.fromBitmap(numberedMarker);
//
//                    markerOptions.icon(markerDescriptor).snippet(person.getPatientName());
//                }
//            }
//        } else {
//            if (person != null) {
//
//                BitmapDescriptor markerDescriptor = null;
//                Bitmap numberedMarker = null;
//                numberedMarker = setMarkerDrawable(false, person.getPatientID());
//
//                markerDescriptor = BitmapDescriptorFactory.fromBitmap(numberedMarker);
//
//                markerOptions.icon(markerDescriptor).snippet(person.getPatientName());
//            }
//        }

    }


    /**
     * Called before the marker for a Cluster is added to the map.
     * The default implementation draws a circle with a rough count of the number of items.
     */
    protected void onBeforeClusterRendered(Cluster<Users> cluster, MarkerOptions markerOptions) {
        LatLng latlon = cluster.getPosition();
        String lat = String.valueOf(latlon.latitude);
        String lon = String.valueOf(latlon.longitude);

        int bucket = getBucket(cluster);
        BitmapDescriptor descriptor = mIcons.get(bucket);
        if (descriptor == null) {
            mColoredCircleBackground.getPaint().setColor(getColor(bucket));
            descriptor = BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(getClusterText(bucket)));
            mIcons.put(bucket, descriptor);
        }
        // TODO: consider adding anchor(.5, .5) (Individual markers will overlap more often)
        markerOptions.icon(descriptor);
        markerOptions.anchor(.5f, .5f);

    }

    /**
     * Called after the marker for a Cluster has been added to the map.
     */
    protected void onClusterRendered(Cluster<Users> cluster, Marker marker) {
        markerFlag = 0;
    }

    /**
     * Called after the marker for a ClusterItem has been added to the map.
     */
    protected void onClusterItemRendered(Users clusterItem, Marker marker) {
    }

    /**
     * Get the marker from a ClusterItem
     *
     * @param clusterItem ClusterItem which you will obtain its marker
     * @return a marker from a ClusterItem or null if it does not exists
     */
    public Marker getMarker(Users clusterItem) {
        return mMarkerCache.get(clusterItem);
    }

    /**
     * Get the ClusterItem from a marker
     *
     * @param marker which you will obtain its ClusterItem
     * @return a ClusterItem from a marker or null if it does not exists
     */
    public Users getClusterItem(Marker marker) {
        return mMarkerCache.get(marker);
    }

    /**
     * Get the marker from a Cluster
     *
     * @param cluster which you will obtain its marker
     * @return a marker from a cluster or null if it does not exists
     */
    public Marker getMarker(Cluster<Users> cluster) {
        return mClusterToMarker.get(cluster);
    }

    /**
     * Get the Cluster from a marker
     *
     * @param marker which you will obtain its Cluster
     * @return a Cluster from a marker or null if it does not exists
     */
    public Cluster<Users> getCluster(Marker marker) {
        return mMarkerToCluster.get(marker);
    }

    /**
     * Creates markerWithPosition(s) for a particular cluster, animating it if necessary.
     */
    private class CreateMarkerTask {
        private final Cluster<Users> cluster;
        private final Set<MarkerWithPosition> newMarkers;
        private final LatLng animateFrom;
        int markerCountflag = 1;

        /**
         * @param c            the cluster to render.
         * @param markersAdded a collection of markers to append any created markers.
         * @param animateFrom  the location to animate the markerWithPosition from, or null if no
         *                     animation is required.
         */
        public CreateMarkerTask(Cluster<Users> c, Set<MarkerWithPosition> markersAdded, LatLng animateFrom) {
            this.cluster = c;
            this.newMarkers = markersAdded;
            this.animateFrom = animateFrom;
            if (patiends != null) {
                for (int i = 0; i < patiends.size(); i++) {
                    Users row = patiends.get(i);
                    Log.v(TAG1, "Users Create Marker " + row.getTitle());
                }
            }
        }

        private void perform(MarkerModifier markerModifier) {
            // Don't show small clusters. Render the markers inside, instead.
            if (!shouldRenderAsCluster(cluster)) {

                for (Users item : cluster.getItems()) {

                    Marker marker = mMarkerCache.get(item);
                    MarkerWithPosition markerWithPosition;
                    if (marker == null) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        if (animateFrom != null) {
                            markerOptions.position(animateFrom);
                        } else {
                            markerOptions.position(item.getPosition());
                        }
                        if (!(item.getTitle() == null) && !(item.getSnippet() == null)) {
                            markerOptions.title(item.getTitle());
                            markerOptions.snippet(item.getSnippet());
                        } else if (!(item.getSnippet() == null)) {
                            markerOptions.title(item.getSnippet());
                        } else if (!(item.getTitle() == null)) {
                            markerOptions.title(item.getTitle());
                        }

                        onBeforeClusterItemRendered(item, markerOptions, markerCountflag);
                        Log.v(TAG1, " Perform Create Marker " + markerCountflag);

                        marker = mClusterManager.getMarkerCollection().addMarker(markerOptions);
//                        if (setSelectedLAT.equalsIgnoreCase(String.valueOf(marker.getPosition().latitude)) || setSelectedLONG.equalsIgnoreCase(String.valueOf(marker.getPosition().longitude))) {
//                            marker.showInfoWindow();
//                        }
                        markerWithPosition = new MarkerWithPosition(marker);
                        mMarkerCache.put(item, marker);
                        if (animateFrom != null) {
                            markerModifier.animate(markerWithPosition, animateFrom, item.getPosition());
                        }
                    } else {
                        Log.v(TAG1, " Perform Create Marker else " + markerCountflag);
                        markerWithPosition = new MarkerWithPosition(marker);
                    }
                    onClusterItemRendered(item, marker);
                    newMarkers.add(markerWithPosition);
                    markerCountflag++;
                }
                return;
            }

            Marker marker = mClusterToMarker.get(cluster);
            MarkerWithPosition markerWithPosition;
            if (marker == null) {
                MarkerOptions markerOptions = new MarkerOptions().
                        position(animateFrom == null ? cluster.getPosition() : animateFrom);

                onBeforeClusterRendered(cluster, markerOptions);

                marker = mClusterManager.getClusterMarkerCollection().addMarker(markerOptions);
//                if (setSelectedLAT.equalsIgnoreCase(String.valueOf(marker.getPosition().latitude)) || setSelectedLONG.equalsIgnoreCase(String.valueOf(marker.getPosition().longitude))) {
//                    marker.showInfoWindow();
//                }
                mMarkerToCluster.put(marker, cluster);
                mClusterToMarker.put(cluster, marker);
                markerWithPosition = new MarkerWithPosition(marker);
                if (animateFrom != null) {
                    markerModifier.animate(markerWithPosition, animateFrom, cluster.getPosition());
                }
            } else {
                markerWithPosition = new MarkerWithPosition(marker);
            }
            onClusterRendered(cluster, marker);
            newMarkers.add(markerWithPosition);
        }
    }

    /**
     * A Marker and its position. Marker.getPosition() must be called from the UI thread, so this
     * object allows lookup from other threads.
     */
    private static class MarkerWithPosition {

        private Marker marker = null;
        private LatLng position;

        private MarkerWithPosition(Marker marker) {
            this.marker = marker;
            position = marker.getPosition();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof MarkerWithPosition) {
                return marker.equals(((MarkerWithPosition) other).marker);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return marker.hashCode();
        }
    }

    private static final TimeInterpolator ANIMATION_INTERP = new DecelerateInterpolator();

    /**
     * Animates a markerWithPosition from one position to another. TODO: improve performance for
     * slow devices (e.g. Nexus S).
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private class AnimationTask extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private final MarkerWithPosition markerWithPosition;
        private final Marker marker;
        private final LatLng from;
        private final LatLng to;
        private boolean mRemoveOnComplete;
        private MarkerManager mMarkerManager;

        private AnimationTask(MarkerWithPosition markerWithPosition, LatLng from, LatLng to) {
            this.markerWithPosition = markerWithPosition;
            this.marker = markerWithPosition.marker;
            this.from = from;
            this.to = to;
        }

        public void perform() {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setInterpolator(ANIMATION_INTERP);
            valueAnimator.addUpdateListener(this);
            valueAnimator.addListener(this);
            valueAnimator.start();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mRemoveOnComplete) {
                Cluster<Users> cluster = mMarkerToCluster.get(marker);
                mClusterToMarker.remove(cluster);
                mMarkerCache.remove(marker);
                mMarkerToCluster.remove(marker);
                mMarkerManager.remove(marker);
            }
            markerWithPosition.position = to;
        }

        public void removeOnAnimationComplete(MarkerManager markerManager) {
            mMarkerManager = markerManager;
            mRemoveOnComplete = true;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fraction = valueAnimator.getAnimatedFraction();
            double lat = (to.latitude - from.latitude) * fraction + from.latitude;
            double lngDelta = to.longitude - from.longitude;

            // Take the shortest path across the 180th meridian.
            if (Math.abs(lngDelta) > 180) {
                lngDelta -= Math.signum(lngDelta) * 360;
            }
            double lng = lngDelta * fraction + from.longitude;
            LatLng position = new LatLng(lat, lng);
            marker.setPosition(position);
        }
    }

    public static Bitmap drawTextToBitmap(int gResId) {
        Resources resources = mContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);
        return bitmap;
    }

    public static Bitmap setMarkerDrawable(Users Users) {
        int background = 0;
//        if (Users.isDiagnossed()) {
//            background = R.drawable.ahw_marker_second;
            background = R.drawable.map_marker_red_sm;
//
//        } else {
////            R.drawable.map_marker_blue_sm;
//            background = R.drawable.map_marker_red_sm;
//        }
        Bitmap icon = drawTextToBitmap(background);

        return icon;
    }

    private void startDropMarkerAnimation(final Marker marker) {
        final LatLng target = marker.getPosition();
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        android.graphics.Point targetPoint = proj.toScreenLocation(target);
        final long duration = (long) (200 + (targetPoint.y * 0.6));
        android.graphics.Point startPoint = proj.toScreenLocation(marker.getPosition());
        startPoint.y = 0;
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final Interpolator interpolator = new LinearOutSlowInInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * target.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * target.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 16ms later == 60 frames per second
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
}
