import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private ArrayList<Double> LonDPPdep = new ArrayList<>();
    private double width, height;
    private int tile_size;
    private boolean success = true;

    public Rasterer() {
        // YOUR CODE HERE
        width = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        height = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        tile_size = MapServer.TILE_SIZE;
        double temp = width / (tile_size * 2);
        for (int i = 0; i < 7; i += 1) {
            LonDPPdep.add(temp);
            temp /= 2;
        }
        //LonDPPdep.get(0) = 0.000171661376953125(depth = 1)
        //LonDPPdep.get(1) = 0.0000858306884765625(depth = 2)
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        // System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           // + "your browser.");
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");

        // find the appropriate depth
        double LonDPP = (lrlon - ullon) / w;
        int depth = 0;
        for (int i = 0; i < LonDPPdep.size(); i += 1) {
            depth = i;
            if (LonDPPdep.get(i) <= LonDPP) {
                break;
            }
        }
        depth += 1;

        //hello world思想，从最简单的开始
        //难点：每一层左上角坐标附近的tile name

        //recursively search which tile the upper left point lies in among the four candidates subimages
        // index! not lon/lat values, is the upper left of the chosen tiles
        int res_ullon = locateX(ullon, MapServer.ROOT_ULLON, MapServer.ROOT_LRLON, depth);
        int res_ullat= locateY(ullat, MapServer.ROOT_LRLAT, MapServer.ROOT_ULLAT, depth);
        int res_lrlon = locateX(lrlon, MapServer.ROOT_ULLON, MapServer.ROOT_LRLON, depth);
        int res_lrlat = locateY(lrlat, MapServer.ROOT_LRLAT, MapServer.ROOT_ULLAT, depth);

        String[][] render_grid = renderGrid(res_ullon, res_ullat, res_lrlon, res_lrlat, depth);
        results.put("render_grid", render_grid);

        double raster_ul_lon = rasterLon(res_ullon, depth);
        results.put("raster_ul_lon", raster_ul_lon);

        double raster_ul_lat = rasterLat(res_ullat, depth);
        results.put("raster_ul_lat", raster_ul_lat);
        // calculate the upper left lat and lon. So for the lower right, plus 1
        double raster_lr_lon = rasterLon(res_lrlon + 1, depth);
        results.put("raster_lr_lon", raster_lr_lon);

        double raster_lr_lat = rasterLat(res_lrlat + 1, depth);
        results.put("raster_lr_lat", raster_lr_lat);

        results.put("depth", depth);

        results.put("query_success", success);
        return results;

    }

    private double rasterLon(int res_lon, int depth) {
        double len = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
        return MapServer.ROOT_ULLON + len * res_lon;
    }

    private double rasterLat(int res_lat, int depth) {
        double len = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);
        return MapServer.ROOT_ULLAT - len * res_lat;
    }

    /* x is ullon of the query, rootbound1 is left, rootbound2 is right;
    * x is  ullat of the query, rootbound1 is lower, rootbound2 is upper.
    * rootbound1 < rootbound2
    * */
    private int locateX(double x, double rootbound1, double rootbound2, int depth) {
        int location = 0;
        if (x < rootbound1 || x > rootbound2) success = false;
        for (int i = 0; i < depth; i += 1) {
            double middle = rootbound1 + (rootbound2 - rootbound1) / 2;
            if (x < middle) {
                rootbound2 = middle;
                location = location * 2;
            }
            if (x >= middle) {
                rootbound1 = middle;
                location = location * 2 + 1;
            }
        }
        return location;
    }
    private int locateY(double y, double rootbound1, double rootbound2, int depth) {
        int location = 0;
        if (y < rootbound1 || y > rootbound2) success = false;
        for (int i = 0; i < depth; i += 1) {
            double middle = rootbound1 + (rootbound2 - rootbound1) / 2;
            if (y < middle) {
                rootbound2 = middle;
                location = location * 2 + 1;
            }
            if (y >= middle) {
                rootbound1 = middle;
                location = location * 2;
            }
        }
        return location;
    }

    /* [[d2_x0_y1.png, d2_x1_y1.png, d2_x2_y1.png, d2_x3_y1.png],
        [d2_x0_y2.png, d2_x1_y2.png, d2_x2_y2.png, d2_x3_y2.png],
        [d2_x0_y3.png, d2_x1_y3.png, d2_x2_y3.png, d2_x3_y3.png]] */
    private String[][] renderGrid(int res_ullon, int res_ullat, int res_lrlon, int res_lrlat, int depth) {
        int cols = res_lrlon - res_ullon + 1;
        int rows = res_lrlat - res_ullat + 1;
        String[][] render_grid = new String[rows][cols];
        for (int i = 0; i < rows; i += 1) {
            for (int j = 0; j < cols; j += 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("d");
                sb.append(depth);
                sb.append("_x");
                sb.append(res_ullon + j);
                sb.append("_y");
                sb.append(res_ullat + i);
                sb.append(".png");
                render_grid[i][j] = sb.toString();
            }
        }
        return render_grid;
    }

}
