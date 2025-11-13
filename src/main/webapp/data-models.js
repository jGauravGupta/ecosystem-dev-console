// Core datasets
let beans = [];
let observerEvents = [];
let seenTypes = [];
let restResources = [];
let restMethods = [];

// New datasets
let scopedBeans = [];
let producers = [];
let interceptors = [];
let interceptedClasses = [];
let decorators = [];
let extensions = [];
let restExceptionMappers = [];

// Sorting and UI state
let currentSortColumn = {};
let currentSortOrder = {};

// Categorized collections
const keys = [
  'Observer', 'Processed', 'SeenTypes', 'RestResources', 'RestMethods', 'RestExceptionMappers',
  'ScopedBeans', 'Producers', 'Interceptors', 'InterceptedClasses', 'Decorators', 'Extensions'
];

const categorized = {};
keys.forEach(k => categorized[k] = []);
