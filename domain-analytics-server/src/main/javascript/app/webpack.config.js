const HtmlWebpackPlugin = require('html-webpack-plugin');
const TsConfigPathsPlugin = require('awesome-typescript-loader').TsConfigPathsPlugin;
const webpack = require('webpack');
const CleanPlugin = require('clean-webpack-plugin');
const MinifyPlugin = require('babel-minify-webpack-plugin');
const CompressionPlugin = require('compression-webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const HardsourceWebpackPlugin = require('hard-source-webpack-plugin');

const environment = process.env.NODE_ENV;
const analyze = process.env.ANALYZE != null;
const isProduction = environment === 'production';

module.exports = {
  mode: isProduction ? 'production' : 'development',
  entry: [
    './src/index.tsx'
  ],
  output: {
    filename: 'bundle.js',
    publicPath: '/',
    path: __dirname + '/dist'
  },

  devtool: isProduction ? undefined : 'source-map',

  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.json'],
    plugins: [
      new TsConfigPathsPlugin()
    ]
  },

  devServer: {
    overlay: true,
    hot: true,
    historyApiFallback: true
  },

  module: {
    rules: [
      {
        test: /\.tsx?$/, use: [
          {
            loader: 'awesome-typescript-loader',
            options: {
              useBabel: true,
              configFileName: 'tsconfig.app.json',
            }
          }
        ]
      },
      {
        test: /\.css$/,
        loader: ['style-loader', 'css-loader']
      },
      {
        test: /\.(eot|ttf|woff|woff2)$/,
        loader: 'file-loader?name=public/fonts/[name].[ext]'
      },
      {
        test: /\.svg$/,
        loader: 'svg-inline-loader',
        options: {
          classPrefix: true,
          idPrefix: true,
        }
      },
      ...isProduction
        ? []
        : [{ enforce: 'pre', test: /\.js$/, loader: 'source-map-loader' }]
    ]
  },

  plugins: [
    new HtmlWebpackPlugin({
      template: 'src/index.html'
    }),
    // new HardsourceWebpackPlugin(),
    ...isProduction
      ? [
        new CleanPlugin(['dist']),
        ...analyze ? [new BundleAnalyzerPlugin()] : []
      ]
      : [
        new webpack.NamedModulesPlugin(),
        new webpack.HotModuleReplacementPlugin(),
      ]
  ]
};
