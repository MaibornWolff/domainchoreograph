const emotion = require('emotion');
const { createSerializer } = require('jest-emotion');

expect.addSnapshotSerializer(createSerializer(emotion));
