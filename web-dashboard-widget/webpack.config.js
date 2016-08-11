
var webpack = require('webpack');

module.exports = {
  entry: './src/main.js',
  target: 'web',
  output: {
    path: './js',
    publicPath: '/js/',
    filename: 'bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.vue$/,
        loader: 'vue'
      }, {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: 'babel'
      }]
  },
  babel: {
    presets: ['es2015'],
    plugins: ['transform-runtime']
  }
};

if (process.env.NODE_ENV === 'production') {
  module.exports.plugins = [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"',
      }
    }),
    new webpack.optimize.UglifyJsPlugin({
      sourceMap: false,
      compress: false,
      cache: false
    }),
    new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.optimize.DedupePlugin()
  ];
} else {
  module.exports.devtool = '#source-map';
}